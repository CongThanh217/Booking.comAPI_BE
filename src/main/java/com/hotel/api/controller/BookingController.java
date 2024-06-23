package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.booking.BookingForHotelDto;
import com.hotel.api.dto.booking.BookingForUserDto;
import com.hotel.api.dto.booking.TotalPriceDto;
import com.hotel.api.form.booking.AddKindToBooking;
import com.hotel.api.form.booking.CreateBookingForm;
import com.hotel.api.model.criteria.BookingCriteria;
import com.hotel.api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/booking")
public class BookingController extends ABasicController{


    @Autowired
    private BookingService bookingService;


    @PostMapping(value = "/create-for-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOOK_C')")
    public ApiMessageDto<String> createBooking(@Valid @RequestBody CreateBookingForm createBookingForm, BindingResult bindingResult) throws MessagingException {
        Long accountId = getCurrentUser();
        ApiMessageDto<String> apiMessageDto = bookingService.createBooking(createBookingForm,accountId);
        return apiMessageDto;
    }
    @PostMapping(value = "/create-for-guest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> createBookingForGuest(@Valid @RequestBody CreateBookingForm createBookingForm, BindingResult bindingResult) throws MessagingException {
        Long accountId = null;
        ApiMessageDto<String> apiMessageDto = bookingService.createBooking(createBookingForm,accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-for-hotel", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOOK_V')")
    public ApiMessageDto<ResponseListDto<List<BookingForHotelDto>>> getBookingForHotel(@Valid BookingCriteria bookingCriteria, Pageable pageable) {
        Long accountId = getCurrentUser();
        ApiMessageDto<ResponseListDto<List<BookingForHotelDto>>> apiMessageDto = bookingService.getBookingForHotel(bookingCriteria,pageable,accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-my-booking", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOOK_MV')")
    public ApiMessageDto<ResponseListDto<List<BookingForUserDto>>> getMyBooking(@Valid BookingCriteria bookingCriteria, Pageable pageable) {
        Long accountId = getCurrentUser();
        ApiMessageDto<ResponseListDto<List<BookingForUserDto>>> apiMessageDto = bookingService.getBookingForUser(bookingCriteria,pageable,accountId);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-by-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<BookingForUserDto> getBookingByCode(@RequestParam("bookingCode") String code) {
        ApiMessageDto<BookingForUserDto> apiMessageDto = bookingService.getBookingByCode(code);
        return apiMessageDto;
    }
    @PutMapping(value = "/cancel-booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> cancelBooking(@RequestParam("bookingId") Long bookingId) {
        ApiMessageDto<String> apiMessageDto = bookingService.cancelBooking(bookingId);
        return apiMessageDto;
    }
    @GetMapping(value = "/calculate-TotalPrice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<TotalPriceDto> calculateTotalPrice(@Valid @RequestBody List<AddKindToBooking> list) {
        ApiMessageDto<TotalPriceDto> apiMessageDto = bookingService.calculateTotalPrice(list);
        return apiMessageDto;
    }
    @PostMapping(value = "/update-absent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateAbsent(@RequestParam("bookingId") long id,@RequestParam("status") Integer status) {
        ApiMessageDto<String> apiMessageDto = bookingService.updateAbsent(id,status);
        return apiMessageDto;
    }
}
