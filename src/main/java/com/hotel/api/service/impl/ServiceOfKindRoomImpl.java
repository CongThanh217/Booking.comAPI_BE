package com.hotel.api.service.impl;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.service.ServiceDto;
import com.hotel.api.form.service.CreateServiceForm;
import com.hotel.api.form.service.CreateServiceHotelForm;
import com.hotel.api.form.service.UpdateServiceForm;
import com.hotel.api.mapper.ServiceMapper;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.KindOfRoom;
import com.hotel.api.model.User;
import com.hotel.api.repository.HotelRepository;
import com.hotel.api.repository.KindOfRoomRepository;
import com.hotel.api.repository.ServiceRepository;
import com.hotel.api.repository.UserRepository;
import com.hotel.api.service.ServiceOfKindRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOfKindRoomImpl implements ServiceOfKindRoom {
    @Autowired
    private KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiMessageDto<String> createService(CreateServiceForm createServiceForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        KindOfRoom kind = kindOfRoomRepository.findById(createServiceForm.getKindId()).orElse(null);
        if(kind ==null)
        {
            apiMessageDto.setMessage("Not found kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        com.hotel.api.model.Service serviceExited = serviceRepository.findByKind_room(createServiceForm.getKindId());
        if (serviceExited!=null)
        {
            apiMessageDto.setMessage("service for room already exists");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_EXITED);
            return apiMessageDto;
        }
        com.hotel.api.model.Service service = serviceMapper.formToEntity(createServiceForm);
        service.setKind_room(kind);
        serviceRepository.save(service);
        apiMessageDto.setMessage("create sevice success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> createServiceforHotel(CreateServiceHotelForm createServiceForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Hotel hotel = hotelRepository.findById(createServiceForm.getHotelId()).orElse(null);
        if(hotel ==null)
        {
            apiMessageDto.setMessage("Not found hotel");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        com.hotel.api.model.Service serviceExited = serviceRepository.findServicesByHotelId(createServiceForm.getHotelId());
        if (serviceExited!=null)
        {
            apiMessageDto.setMessage("service for room already exists");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_EXITED);
            return apiMessageDto;
        }
        com.hotel.api.model.Service service = serviceMapper.formToEntityForHotel(createServiceForm);
        service.setHotel(hotel);
        serviceRepository.save(service);
        apiMessageDto.setMessage("create sevice success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> updateService(UpdateServiceForm updateServiceForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        com.hotel.api.model.Service service = serviceRepository.findById(updateServiceForm.getServiceId()).orElse(null);
        if(service==null)
        {
            apiMessageDto.setMessage("Not found service kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (service.getKind_room()!=null)
        {
            if (!updateServiceForm.equals(service.getKind_room().getId()))
            {
                KindOfRoom kind = kindOfRoomRepository.findById(updateServiceForm.getKindId()).orElse(null);
                if(kind ==null)
                {
                    apiMessageDto.setMessage("Not found kind of room");
                    apiMessageDto.setResult(false);
                    apiMessageDto.setCode(ErrorCode.KIND_OF_ROOM_ERROR_NOT_FOUND);
                    return apiMessageDto;
                }
                service.setKind_room(kind);
            }
        }
        serviceMapper.UpdateServiceDtoToEntity(updateServiceForm,service);
        serviceRepository.save(service);
        apiMessageDto.setMessage("update service success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ServiceDto> getService(Long id) {
        ApiMessageDto<ServiceDto> apiMessageDto = new ApiMessageDto();
        com.hotel.api.model.Service service = serviceRepository.findByKind_room(id);
        if (service==null)
        {
            apiMessageDto.setMessage("Not found service kind of room");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        ServiceDto serviceDto = serviceMapper.toDto(service);
        apiMessageDto.setData(serviceDto);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ServiceDto> getServiceByHotel(Long id) {
        ApiMessageDto<ServiceDto> apiMessageDto = new ApiMessageDto();
        com.hotel.api.model.Service service = serviceRepository.findServicesByHotelId(id);
        if (service==null)
        {
            apiMessageDto.setMessage("Not found service ");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.SERVICE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        ServiceDto serviceDto = serviceMapper.toDto(service);
        apiMessageDto.setData(serviceDto);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ServiceDto> getServiceForHotel(Long accountId) {

        ApiMessageDto<ServiceDto> apiMessageDto = new ApiMessageDto<>();

        User user = userRepository.findByAccountId(accountId).orElse(null);
        if(user==null)
        {
            apiMessageDto.setMessage("Not found user ");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Hotel hotel = hotelRepository.findByUserId(user.getId());
        if(hotel ==null)
        {
            apiMessageDto.setMessage("Not found hotel ");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.HOTEL_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        com.hotel.api.model.Service service = serviceRepository.findServicesByHotelId(hotel.getId());
        ServiceDto serviceDto = serviceMapper.toDto(service);

        apiMessageDto.setData(serviceDto);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }
}
