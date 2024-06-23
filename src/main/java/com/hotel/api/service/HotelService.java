package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.hotel.HotelDto;
import com.hotel.api.dto.hotel.HotelWithProvinceDto;
import com.hotel.api.form.hotel.ChangeStatusHoteForm;
import com.hotel.api.form.hotel.CreateHotelForm;
import com.hotel.api.form.hotel.UpdateHotelForm;
import com.hotel.api.model.criteria.HotelCriteria;
import com.hotel.api.model.criteria.KindOfRoomCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface HotelService {
    ApiMessageDto<String> createHotel(CreateHotelForm createHotelForm, long accountId);
    ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotel(HotelCriteria hotelCriteria, Pageable pageable);
    ApiMessageDto<String> updateHotel(UpdateHotelForm updateHotelForm);
    ApiMessageDto<String> changeStatus(ChangeStatusHoteForm changeStatusHoteForm);
    ApiMessageDto<HotelDto> getHotel(Long id);
    ApiMessageDto<HotelDto> getMyHotel(Long accountId);
    ApiMessageDto<List<HotelWithProvinceDto>> countHotelInProvince();

    ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotelFilter(Date startDate ,Date endate,Pageable pageable);

}
