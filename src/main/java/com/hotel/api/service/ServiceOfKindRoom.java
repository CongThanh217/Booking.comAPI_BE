package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.service.ServiceDto;
import com.hotel.api.form.service.CreateServiceForm;
import com.hotel.api.form.service.CreateServiceHotelForm;
import com.hotel.api.form.service.UpdateServiceForm;

import java.util.List;

public interface ServiceOfKindRoom {

    ApiMessageDto<String> createService(CreateServiceForm createServiceForm);
    ApiMessageDto<String> createServiceforHotel(CreateServiceHotelForm createServiceForm);
    ApiMessageDto<String> updateService(UpdateServiceForm updateServiceForm);

    ApiMessageDto<ServiceDto> getService(Long id);
    ApiMessageDto<ServiceDto> getServiceByHotel(Long id);
    ApiMessageDto<ServiceDto> getServiceForHotel(Long accountId);

}
