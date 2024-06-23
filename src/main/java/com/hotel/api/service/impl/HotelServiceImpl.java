package com.hotel.api.service.impl;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.hotel.HotelDto;
import com.hotel.api.dto.hotel.HotelWithProvinceDto;
import com.hotel.api.form.hotel.ChangeStatusHoteForm;
import com.hotel.api.form.hotel.CreateHotelForm;
import com.hotel.api.form.hotel.UpdateHotelForm;
import com.hotel.api.mapper.HotelMapper;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.KindOfRoom;
import com.hotel.api.model.Nation;
import com.hotel.api.model.User;
import com.hotel.api.model.criteria.HotelCriteria;
import com.hotel.api.model.criteria.KindOfRoomCriteria;
import com.hotel.api.repository.HotelRepository;
import com.hotel.api.repository.KindOfRoomRepository;
import com.hotel.api.repository.NationRepository;
import com.hotel.api.repository.UserRepository;
import com.hotel.api.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private KindOfRoomRepository kindOfRoomRepository;


    @Override
    public ApiMessageDto<String> createHotel(CreateHotelForm createHotelForm, long accountId) {
        ApiMessageDto<String> apiMessageDto= new ApiMessageDto<>();
        Hotel hotel = hotelMapper.fromHotelDtoToEntity(createHotelForm);
        User user = userRepository.findByAccountId(accountId).orElse(null);
       if(user==null)
       {
           apiMessageDto.setResult(false);
           apiMessageDto.setMessage("người dùng không tồn tại");
           return apiMessageDto;
       }
        Hotel hotelExited = hotelRepository.findByUserId(user.getId());
        if (hotelExited!=null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("chỉ được tạo một khách sạn");
            return apiMessageDto;
        }
        Nation ward = nationRepository.findById(createHotelForm.getWardId()).orElse(null);
        if (ward == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Ward not found");
            return apiMessageDto;
        }
        Nation district = nationRepository.findById(createHotelForm.getDistrictId()).orElse(null);
        if (district == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("District not found");
            return apiMessageDto;
        }
        Nation province = nationRepository.findById(createHotelForm.getProvinceId()).orElse(null);
        if (province == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Province not found");
            return apiMessageDto;
        }
        hotel.setProvince(province);
        hotel.setDistrict(district);
        hotel.setWard(ward);
        hotel.setUser(user);
        hotelRepository.save(hotel);
        apiMessageDto.setMessage(String.valueOf(hotel.getId()));
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotel(HotelCriteria hotelCriteria,Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<HotelDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<HotelDto>> responseListDto = new ResponseListDto<>();

       Page<Hotel> page = hotelRepository.findAll(hotelCriteria.getSpecification(),pageable);
       responseListDto.setContent(hotelMapper.fromEntityToListDto(page.getContent()));
       responseListDto.setTotalPages(page.getTotalPages());
       responseListDto.setTotalElements(page.getTotalElements());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("get hotel success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> updateHotel(UpdateHotelForm updateHotelForm) {
        ApiMessageDto<String> apiMessageDto= new ApiMessageDto<>();
        Hotel hotel = hotelRepository.findById(updateHotelForm.getId()).orElse(null);
        if(hotel==null)
        {
            apiMessageDto.setMessage("Hotel not found");
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        hotelMapper.UpdateHotelDtoToEntity(updateHotelForm,hotel);
        if (updateHotelForm.getProvinceId()!=hotel.getProvince().getId())
        {
            Nation province = nationRepository.findById(updateHotelForm.getProvinceId()).orElse(null);
            if (province == null){
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("Province not found");
                return apiMessageDto;
            }
            hotel.setProvince(province);
        }
        if (updateHotelForm.getDistrictId()!=hotel.getDistrict().getId())
        {
            Nation district = nationRepository.findById(updateHotelForm.getDistrictId()).orElse(null);
            if (district == null){
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("District not found");
                return apiMessageDto;
            }

            hotel.setDistrict(district);
        }
        if (updateHotelForm.getDistrictId()!=hotel.getDistrict().getId())
        {
            Nation ward = nationRepository.findById(updateHotelForm.getWardId()).orElse(null);
            if (ward == null){
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.NATION_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("Ward not found");
                return apiMessageDto;
            }
            hotel.setWard(ward);
        }
        hotelRepository.save(hotel);
        apiMessageDto.setMessage("update hotel sucess");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> changeStatus(ChangeStatusHoteForm changeStatusHoteForm) {
       ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
       Hotel hotel = hotelRepository.findById(changeStatusHoteForm.getId()).orElse(null);
       if (hotel==null)
       {
           apiMessageDto.setMessage("Not found hotel");
           apiMessageDto.setResult(false);
           apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
           return apiMessageDto;
       }
       hotel.setStatus(changeStatusHoteForm.getStatus());
       hotelRepository.save(hotel);
       apiMessageDto.setMessage("change status success");
       return apiMessageDto;
    }

    @Override
    public ApiMessageDto<HotelDto> getHotel(Long id) {
        ApiMessageDto<HotelDto> apiMessageDto = new ApiMessageDto<>();
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if(hotel ==null)
        {
            apiMessageDto.setMessage("Not found hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(hotelMapper.fromEntityToDto(hotel));
        apiMessageDto.setMessage("get hotel success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<HotelDto> getMyHotel(Long accountId) {
        ApiMessageDto<HotelDto> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findByAccountId(accountId).orElse(null);
        if(user==null)
        {
            apiMessageDto.setMessage("user Not found");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        apiMessageDto.setData(hotelMapper.fromEntityToDto(hotel));
        apiMessageDto.setMessage("get my hotel success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<HotelWithProvinceDto>> countHotelInProvince() {
        ApiMessageDto<List<HotelWithProvinceDto>> apiMessageDto= new ApiMessageDto<>();

        List<HotelWithProvinceDto> listCount = hotelRepository.countHotelForProvince();


        apiMessageDto.setData(listCount);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotelFilter(Date startDate , Date endDate,Pageable pageable) {

        ApiMessageDto<ResponseListDto<List<HotelDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<HotelDto>> responseListDto = new ResponseListDto<>();

        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long distance = (long) (endLocalDate.getDayOfMonth()- startLocalDate.getDayOfMonth());
        Page<Object[]> hotelPage = hotelRepository.findHotelsWithEmptyRooms(startDate, endDate, distance, UserBaseConstant.STATUS_ACTIVE, pageable);

        List<HotelDto> hotelDtoList = hotelPage.getContent().stream().map(objects -> {
            Hotel hotel = (Hotel) objects[0];
            Integer roomPrice = (Integer) objects[1];
            HotelDto hotelDto = hotelMapper.fromEntityToDto(hotel);
            hotelDto.setRoomPrice(roomPrice);
            return hotelDto;
        }).collect(Collectors.toList());
        responseListDto.setContent(hotelDtoList);
        responseListDto.setTotalElements(hotelPage.getTotalElements());
        responseListDto.setTotalPages(hotelPage.getTotalPages());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("get list success");
        return apiMessageDto;
    }
}
