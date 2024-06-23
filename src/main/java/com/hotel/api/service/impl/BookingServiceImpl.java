package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.booking.BookingDetailDto;
import com.hotel.api.dto.booking.BookingForHotelDto;
import com.hotel.api.dto.booking.BookingForUserDto;
import com.hotel.api.dto.booking.TotalPriceDto;
import com.hotel.api.form.booking.AddKindToBooking;
import com.hotel.api.form.booking.CreateBookingForm;
import com.hotel.api.mapper.BookingDetailMapper;
import com.hotel.api.mapper.BookingMapper;
import com.hotel.api.model.*;
import com.hotel.api.model.criteria.BookingCriteria;
import com.hotel.api.repository.*;
import com.hotel.api.service.BookingService;
import com.hotel.api.service.EmailService;
import com.hotel.api.service.UserBaseOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private  BookingRepository bookingRepository;
    @Autowired
    private  BookingDetailRepository bookingDetailRepository;
    @Autowired
    private  KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private BookingDetailMapper bookingDetailMapper;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserBaseOTPService userBaseOTPService;
    @Autowired
    private EmptyRoomRepository emptyRoomRepository;
    @Autowired
    private EmailService emailService;
    @Override
    public ApiMessageDto<String> createBooking(CreateBookingForm createBookingForm ,Long accountId) throws MessagingException {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Booking booking = new Booking();


        Hotel hotel = hotelRepository.findById(createBookingForm.getHotelId()).orElse(null);
        if (hotel==null)
        {
            apiMessageDto.setMessage("Hotel not found");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        booking = bookingMapper.formToEntity(createBookingForm);
        booking.setHotel(hotel);
        if (accountId!= null)
        {
            User user = userRepository.findByAccountId(accountId).orElse(null);
            if (user==null)
            {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Not found user");
                apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            booking.setUser(user);
        }
        bookingRepository.save(booking);
        List<BookingDetail> detailList = new ArrayList<>();
        Integer totalPrice =0 ;
        EmptyRoom emptyRoom ;
        for(AddKindToBooking item : createBookingForm.getKindToBookingList())
        {
            BookingDetail bookingDetail ;
            KindOfRoom kindOfRoom = kindOfRoomRepository.findById(item.getKindOfRoomId()).orElse(null);
            if (kindOfRoom==null)
            {
                apiMessageDto.setMessage("Kind of room not found");
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            com.hotel.api.model.Service service = serviceRepository.findByKind_room(item.getKindOfRoomId());
            if (service==null)
            {
                apiMessageDto.setMessage("service of kind not found");
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            System.out.println("emptyRoom : "+ item.getEmptyRoomId());
            emptyRoom = emptyRoomRepository.findById(item.getEmptyRoomId()).orElse(null);
            if(emptyRoom ==null)
            {
                apiMessageDto.setMessage("Not found this empty Room");
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            bookingDetail= bookingDetailMapper.fromServiceToEntity(service);
            bookingDetail.setRoomName(kindOfRoom.getName());
            bookingDetail.setBooking(booking);
            bookingDetail.setPrice(emptyRoom.getPrice());
            bookingDetail.setSize(kindOfRoom.getSize());
            bookingDetail.setNumberOfBed(kindOfRoom.getNumberOfBed());
            bookingDetail.setRoomNumber(item.getQuantity());
//            System.out.println("kêt quaả" + bookingDetail.getSize());
            detailList.add(bookingDetail);
            totalPrice += emptyRoom.getPrice()*item.getQuantity();
            int countEmptyRoom = emptyRoom.getEmptyRoom()-item.getQuantity();
            emptyRoom.setEmptyRoom(countEmptyRoom);
            if(countEmptyRoom <0)
            {
                apiMessageDto.setMessage("number of rooms is not enough");
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            if(countEmptyRoom==0)
            {
                emptyRoom.setStatus(UserBaseConstant.STATUS_LOCK);
            }
            emptyRoomRepository.save(emptyRoom);
        }
        booking.setBookingCode(userBaseOTPService.genCodeOrder(6));
        booking.setPrice(totalPrice);
        booking.setStatus(UserBaseConstant.STATUS_ACTIVE);
        bookingRepository.save(booking);
        bookingDetailRepository.saveAll(detailList);
        if (createBookingForm.getEmail()!=null)
        {
            emailService.sendOrderPaidToEmail(booking,booking.getEmail());
        }
        apiMessageDto.setMessage("book hotel success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<BookingForHotelDto>>> getBookingForHotel(BookingCriteria bookingCriteria , Pageable pageable ,long accountId) {
        ApiMessageDto<ResponseListDto<List<BookingForHotelDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<BookingForHotelDto>> responseListDto = new ResponseListDto<>();

        User user = userRepository.findByAccountId(accountId).orElse(null);
        if (user==null)
        {
            apiMessageDto.setMessage("not found user");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (hotel==null)
        {
            apiMessageDto.setMessage("not found hotel by this user");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        bookingCriteria.setHotelId(hotel.getId());
        Page<Booking> listBooking = bookingRepository.findAll(bookingCriteria.getSpecification(),pageable);
        List<BookingForHotelDto> list = bookingMapper.entitytoBookingForHotelDtoList(listBooking.getContent());
        for (BookingForHotelDto c :list)
        {
            List<BookingDetail> detailList = bookingDetailRepository.findAllByBookingId(c.getId());
            List<BookingDetailDto> detailDtoList = bookingDetailMapper.entityToBookingDetailistDto(detailList);
            c.setListBookingDetail(detailDtoList);
        }
        responseListDto.setContent(list);
        responseListDto.setTotalElements(listBooking.getTotalElements());
        responseListDto.setTotalElements(listBooking.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("get list success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<BookingForUserDto>>> getBookingForUser(BookingCriteria bookingCriteria, Pageable pageable, long accountId) {

        ApiMessageDto<ResponseListDto<List<BookingForUserDto>>> apiMessageDto= new ApiMessageDto<>();
        ResponseListDto<List<BookingForUserDto>> responseListDto = new ResponseListDto<>();
        User user = userRepository.findByAccountId(accountId).orElse(null);
        if (user==null)
        {
            apiMessageDto.setMessage("not found user");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Page<Booking> listBooking = bookingRepository.findAllByUserId(user.getId() , pageable);
        List<BookingForUserDto> dtoList = bookingMapper.entitytoBookingForUserDtoList(listBooking.getContent());
        for(BookingForUserDto c: dtoList)
        {
            List<BookingDetail> detailList = bookingDetailRepository.findAllByBookingId(c.getId());
            List<BookingDetailDto> detailDtoList = bookingDetailMapper.entityToBookingDetailistDto(detailList);
            c.setListBookingDetail(detailDtoList);
        }
        responseListDto.setContent(dtoList);
        responseListDto.setTotalElements(listBooking.getTotalElements());
        responseListDto.setTotalElements(listBooking.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("get list success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<BookingForUserDto> getBookingByCode(String code) {
        ApiMessageDto<BookingForUserDto> apiMessageDto = new ApiMessageDto<>();
        Booking booking = bookingRepository.findBookingByBookingCode(code);
        if (booking==null)
        {
            apiMessageDto.setMessage("Not found booking code");
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(ErrorCode.BOOKING_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        List<BookingDetail> detailList = bookingDetailRepository.findAllByBookingId(booking.getId());
        BookingForUserDto bookingForUserDto = bookingMapper.fromEntityToBookingForUserDto(booking);
        bookingForUserDto.setListBookingDetail(bookingDetailMapper.entityToBookingDetailistDto(detailList));

        apiMessageDto.setData(bookingForUserDto);
        apiMessageDto.setMessage("Get booking success");

        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> cancelBooking(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking==null)
        {
            apiMessageDto.setMessage("Not found booking");
            apiMessageDto.setResult(false);
            apiMessageDto.setData(ErrorCode.BOOKING_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (booking.getStatus() == UserBaseConstant.STATUS_CANCEL)
        {
            apiMessageDto.setMessage("booking has been cancelled");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (booking.getStatus() == UserBaseConstant.STATUS_COMPLETED)
        {
            apiMessageDto.setMessage("booking has been completed");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        booking.setStatus(UserBaseConstant.STATUS_CANCEL);
        bookingRepository.save(booking);
        apiMessageDto.setMessage("cancel booking succes");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TotalPriceDto> calculateTotalPrice(List<AddKindToBooking> addKindToBookingList) {
        ApiMessageDto<TotalPriceDto> apiMessageDto = new ApiMessageDto<>();

        Integer totalPrice =0 ;
        EmptyRoom emptyRoom ;
        for(AddKindToBooking item : addKindToBookingList)
        {
            KindOfRoom kindOfRoom = kindOfRoomRepository.findById(item.getKindOfRoomId()).orElse(null);
            if (kindOfRoom==null)
            {
                apiMessageDto.setMessage("Kind of room not found");
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            emptyRoom = emptyRoomRepository.findById(item.getEmptyRoomId()).orElse(null);
            if(emptyRoom ==null)
            {
                apiMessageDto.setMessage("Not found this empty Room");
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
           if(emptyRoom.getEmptyRoom() ==null)
           {
               apiMessageDto.setMessage("There are no rooms left");
               apiMessageDto.setResult(false);
               return apiMessageDto;
           }
            totalPrice += emptyRoom.getPrice()*item.getQuantity() ;
        }

        TotalPriceDto totalPriceDto =new TotalPriceDto();
        totalPriceDto.setTotalPrice(totalPrice);

        apiMessageDto.setMessage("calculate success");
        apiMessageDto.setData(totalPriceDto);
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> updateAbsent(Long bookingId,Integer status) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();


        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking==null)
        {
            apiMessageDto.setMessage("not found booing");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.BOOKING_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate startLocalDate = booking.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (currentDate.isBefore(startLocalDate)) {
            apiMessageDto.setMessage("cannot report a no-show before the check-in date");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }

        if (booking.getStatus() == UserBaseConstant.STATUS_ABSENT)
        {
            apiMessageDto.setMessage("booking has been absented ");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (booking.getStatus() == UserBaseConstant.STATUS_CANCEL)
        {
            apiMessageDto.setMessage("booking has been cancelled ");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (booking.getStatus() == UserBaseConstant.STATUS_COMPLETED)
        {
            apiMessageDto.setMessage("booking has been completed");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        booking.setStatus(status);
        bookingRepository.save(booking);

        apiMessageDto.setMessage("update booking success");
        return apiMessageDto;
    }
}
