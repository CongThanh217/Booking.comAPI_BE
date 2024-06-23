package com.hotel.api.mapper;

import com.hotel.api.dto.booking.BookingDetailDto;
import com.hotel.api.model.BookingDetail;
import com.hotel.api.model.KindOfRoom;
import com.hotel.api.model.Service;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {KindOfRoom.class,Service.class})
public interface BookingDetailMapper {


    @Mapping(source = "price", target = "price")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "numberOfBed", target = "numberOfBed")
    @BeanMapping(ignoreByDefault = true)
    BookingDetail fromKindToEntity(KindOfRoom kindOfRoom);
    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "rootTop", target = "rootTop")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "hotBathRoom", target = "hotBathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "bacony", target = "bacony")
    @Mapping(source = "seaview", target = "seaview")
    @Mapping(source = "airCondition", target = "airCondition")
    @BeanMapping(ignoreByDefault = true)
    BookingDetail fromServiceToEntity(Service service);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "breakfast", target = "breakfast")
    @Mapping(source = "bar", target = "bar")
    @Mapping(source = "steamRoom", target = "steamRoom")
    @Mapping(source = "wifi", target = "wifi")
    @Mapping(source = "rootTop", target = "rootTop")
    @Mapping(source = "pool", target = "pool")
    @Mapping(source = "hotBathRoom", target = "hotBathRoom")
    @Mapping(source = "kitchenette", target = "kitchenette")
    @Mapping(source = "bacony", target = "bacony")
    @Mapping(source = "seaview", target = "seaview")
    @Mapping(source = "airCondition", target = "airCondition")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "numberOfBed", target = "numberOfBed")
    @Mapping(source = "roomName", target = "roomName")
    @BeanMapping(ignoreByDefault = true)
    @Named("entityToBookingDetailDto")
    BookingDetailDto fromEntityToDto(BookingDetail bookingDetail);


    @IterableMapping(elementTargetType = BookingDetailDto.class, qualifiedByName = "entityToBookingDetailDto")
    List<BookingDetailDto> entityToBookingDetailistDto(List<BookingDetail> bookings);
}
