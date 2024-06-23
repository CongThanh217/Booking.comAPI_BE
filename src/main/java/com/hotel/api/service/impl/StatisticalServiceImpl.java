package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.statistical.RevenueOfYearDto;
import com.hotel.api.dto.statistical.RoomBookingCountDto;
import com.hotel.api.dto.statistical.TotalBookingDto;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.User;
import com.hotel.api.repository.BookingRepository;
import com.hotel.api.repository.HotelRepository;
import com.hotel.api.repository.UserRepository;
import com.hotel.api.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public ApiMessageDto<TotalBookingDto> getStatiticOfBooking(Integer status, Long accountId ,Integer month, Integer year) {

        ApiMessageDto<TotalBookingDto> apiMessageDto = new ApiMessageDto<>();

        User user = userRepository.findByAccountId(accountId).orElse(null);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (hotel==null)
        {
            apiMessageDto.setMessage("Not found Hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        List<RoomBookingCountDto> countDtoList = bookingRepository.roomBookingCount(status,hotel.getId(), month,year);
        Long totalCount = bookingRepository.countBookingByHotelAndStatus(status,hotel.getId(),month,year);
        Long totalPrice = bookingRepository.totalPriceBooking(status,hotel.getId(),month,year);
        TotalBookingDto totalBookingDto = new TotalBookingDto() ;
        totalBookingDto.setRoomBookingCountDto(countDtoList);
        totalBookingDto.setTotalCountBooking(totalCount);
        totalBookingDto.setTotalPriceBooking(totalPrice);

        apiMessageDto.setData(totalBookingDto);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<RevenueOfYearDto>> getRevenueMonth(Integer year, Long account) {
        ApiMessageDto<List<RevenueOfYearDto>> apiMessageDto= new ApiMessageDto<>();
        User user = userRepository.findByAccountId(account).orElse(null);
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if (hotel==null)
        {
            apiMessageDto.setMessage("Not found Hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        List<RevenueOfYearDto> list= bookingRepository.calculateYearRevenue(UserBaseConstant.STATUS_COMPLETED,year,hotel.getId());
        apiMessageDto.setData(list);
        apiMessageDto.setMessage("get revenue success");
        return apiMessageDto;
    }
}
