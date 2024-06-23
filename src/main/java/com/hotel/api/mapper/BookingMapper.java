package com.hotel.api.mapper;

import com.hotel.api.dto.booking.BookingForHotelDto;
import com.hotel.api.dto.booking.BookingForUserDto;
import com.hotel.api.form.booking.CreateBookingForm;
import com.hotel.api.model.Booking;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {HotelMapper.class})
public interface BookingMapper {

    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "checkIn", target = "checkIn")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    Booking formToEntity(CreateBookingForm createBookingForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "bookingCode", target = "bookingCode")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "checkIn", target = "checkIn")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityBookingForHotelDto")
    BookingForHotelDto fromEntityToDto(Booking booking);

    @IterableMapping(elementTargetType = BookingForHotelDto.class, qualifiedByName = "fromEntityBookingForHotelDto")
    List<BookingForHotelDto> entitytoBookingForHotelDtoList(List<Booking> bookings);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "bookingCode", target = "bookingCode")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "checkIn", target = "checkIn")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "hotel", target = "hotelDtoAuto",qualifiedByName = "fromEntityToDtoForUser")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityBookingForUserDto")
    BookingForUserDto fromEntityToBookingForUserDto(Booking booking);
    @IterableMapping(elementTargetType = BookingForUserDto.class, qualifiedByName = "fromEntityBookingForUserDto")
    List<BookingForUserDto> entitytoBookingForUserDtoList(List<Booking> bookings);
}
