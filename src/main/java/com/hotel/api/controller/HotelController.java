package com.hotel.api.controller;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.hotel.HotelDto;
import com.hotel.api.dto.hotel.HotelWithProvinceDto;
import com.hotel.api.form.hotel.ChangeStatusHoteForm;
import com.hotel.api.form.hotel.CreateHotelForm;
import com.hotel.api.form.hotel.UpdateHotelForm;
import com.hotel.api.model.criteria.HotelCriteria;
import com.hotel.api.model.criteria.KindOfRoomCriteria;
import com.hotel.api.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/hotel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class HotelController extends ABasicController{

    @Autowired
    private HotelService hotelService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOTEL_AD')")
    public ApiMessageDto<String> createHotel(@Valid @RequestBody CreateHotelForm createHotelForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Long accountId = getCurrentUser();
        if(accountId==null)
        {
            apiMessageDto.setMessage("Please create an account to post your hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto = hotelService.createHotel(createHotelForm, accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOTEL_V')")
    public ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotel(@Valid HotelCriteria hotelCriteria , Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<HotelDto>>> apiMessageDto= hotelService.getListHotel(hotelCriteria,pageable);
        return apiMessageDto;
    }
    @GetMapping(value = "/list-public", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<HotelDto>>> getListHotelPublic(@Valid HotelCriteria hotelCriteria , Pageable pageable) {
        hotelCriteria.setStatus(UserBaseConstant.STATUS_ACTIVE);
        ApiMessageDto<ResponseListDto<List<HotelDto>>> apiMessageDto= hotelService.getListHotel(hotelCriteria,pageable);
        return apiMessageDto;
    }
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOTEL_U')")
    public ApiMessageDto<String> updateHotel(@Valid @RequestBody UpdateHotelForm updateHotelForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = hotelService.updateHotel(updateHotelForm);
        return apiMessageDto;
    }
    @PutMapping(value = "/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOTEL_C_ST')")
    public ApiMessageDto<String> changeStatus(@Valid @RequestBody ChangeStatusHoteForm changeStatusHoteForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = hotelService.changeStatus(changeStatusHoteForm);
        return apiMessageDto;
    }
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<HotelDto> getHotel(@PathVariable("id") Long id) {
        ApiMessageDto<HotelDto> apiMessageDto= hotelService.getHotel(id);
        return apiMessageDto;
    }
    @GetMapping(value = "/my-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<HotelDto> getMyHotelHotel() {
        Long accountId =getCurrentUser();
        ApiMessageDto<HotelDto> apiMessageDto= hotelService.getMyHotel(accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/count-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<HotelWithProvinceDto>> countHotelInProvince() {
        ApiMessageDto<List<HotelWithProvinceDto>> apiMessageDto= hotelService.countHotelInProvince();
        return apiMessageDto;
    }

    @GetMapping(value = "/filter-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<HotelDto>>> filterHotel(@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate,Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<HotelDto>>> apiMessageDto= hotelService.getListHotelFilter(startDate,endDate,pageable);
        return apiMessageDto;
    }
}
