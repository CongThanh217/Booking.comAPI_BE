package com.hotel.api.mapper;

import com.hotel.api.dto.service.ServiceDto;
import com.hotel.api.form.service.CreateServiceForm;
import com.hotel.api.form.service.CreateServiceHotelForm;
import com.hotel.api.form.service.UpdateServiceForm;
import com.hotel.api.model.Service;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "rooftop", target = "rootTop")
    @Mapping(source = "airCondition", target = "airCondition")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "hotbatRoom", target = "hotBathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "balcony", target = "bacony")
    @Mapping(source = "seaView", target = "seaview")
    @BeanMapping(ignoreByDefault = true)
    @Named("toEntity")
    Service formToEntity(CreateServiceForm createServiceForm);
    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "rooftop", target = "rootTop")
    @Mapping(source = "airCondition", target = "airCondition")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "hotbathRoom", target = "hotBathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "balcony", target = "bacony")
    @Mapping(source = "seaView", target = "seaview")
    @BeanMapping(ignoreByDefault = true)
    @Named("toEntity")
    Service formToEntityForHotel(CreateServiceHotelForm createServiceForm);
    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "rooftop", target = "rootTop")
    @Mapping(source = "airCondition", target = "airCondition")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "hotbathRoom", target = "hotBathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "balcony", target = "bacony")
    @Mapping(source = "seaView", target = "seaview")
    @BeanMapping(ignoreByDefault = true)
    void UpdateServiceDtoToEntity(UpdateServiceForm updateServiceForm, @MappingTarget Service service);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "rootTop", target = "rooftop")
    @Mapping(source = "airCondition", target = "airCondition")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "hotBathRoom", target = "hotbathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "bacony", target = "balcony")
    @Mapping(source = "seaview", target = "seaView")
    @BeanMapping(ignoreByDefault = true)
    @Named("toEntity")
    ServiceDto toDto(Service service);
}
