package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.booking.BookingForHotelDto;
import com.hotel.api.dto.booking.BookingForUserDto;
import com.hotel.api.dto.booking.TotalPriceDto;
import com.hotel.api.form.booking.AddKindToBooking;
import com.hotel.api.form.booking.CreateBookingForm;
import com.hotel.api.model.criteria.BookingCriteria;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;

public interface BookingService {

    ApiMessageDto<String> createBooking(CreateBookingForm createBookingForm, Long accountId) throws MessagingException;
    ApiMessageDto<ResponseListDto<List<BookingForHotelDto>>> getBookingForHotel(BookingCriteria bookingCriteria, Pageable pageable ,long accountId);

    ApiMessageDto<ResponseListDto<List<BookingForUserDto>>> getBookingForUser(BookingCriteria bookingCriteria , Pageable pageable , long accountId);
    ApiMessageDto<BookingForUserDto> getBookingByCode(String code);
    ApiMessageDto<String> cancelBooking(Long code);
    ApiMessageDto<TotalPriceDto> calculateTotalPrice(List<AddKindToBooking> addKindToBookingList);
    ApiMessageDto<String> updateAbsent(Long bookingId,Integer status);


}
