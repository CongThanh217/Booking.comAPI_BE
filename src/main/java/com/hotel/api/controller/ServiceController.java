package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.service.ServiceDto;
import com.hotel.api.form.service.CreateServiceForm;
import com.hotel.api.form.service.CreateServiceHotelForm;
import com.hotel.api.form.service.UpdateServiceForm;
import com.hotel.api.model.Account;
import com.hotel.api.service.ServiceOfKindRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ServiceController extends ABasicController{

    @Autowired
    private ServiceOfKindRoom serviceOfKindRoom;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SV_AD')")
    public ApiMessageDto<String> addServiceKind(@Valid @RequestBody CreateServiceForm createServiceForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = serviceOfKindRoom.createService(createServiceForm);
        return apiMessageDto;
    }
    @PostMapping(value = "/create-for-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SVH_AD')")
    public ApiMessageDto<String> addServiceHotel(@Valid @RequestBody CreateServiceHotelForm createServiceForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = serviceOfKindRoom.createServiceforHotel(createServiceForm);
        return apiMessageDto;
    }
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SV_U')")
    public ApiMessageDto<String> updateService(@Valid @RequestBody UpdateServiceForm updateServiceForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = serviceOfKindRoom.updateService(updateServiceForm);
        return apiMessageDto;
    }

    @GetMapping(value = "/get-by-kindId", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('IM_AD')")
    public ApiMessageDto<ServiceDto> getService(@RequestParam("kindId") long kindId) {
        ApiMessageDto<ServiceDto> apiMessageDto = serviceOfKindRoom.getService(kindId);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-by-HotelId", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('IM_AD')")
    public ApiMessageDto<ServiceDto> getServiceByHotel(@RequestParam("hotelId") long hotelId) {
        ApiMessageDto<ServiceDto> apiMessageDto = serviceOfKindRoom.getServiceByHotel(hotelId);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-for-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ServiceDto> getServiceForHotel() {
        ApiMessageDto<ServiceDto> apiMessageDto = new ApiMessageDto<>();
        Long accountId = getCurrentUser();
        if (accountId ==null)
        {
            apiMessageDto.setMessage("not found account");
            return apiMessageDto;
        }
        apiMessageDto = serviceOfKindRoom.getServiceForHotel(accountId);
        return apiMessageDto;
    }

}
