package com.hotel.api.dto.booking;

import com.hotel.api.dto.hotel.HotelDtoAuto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingForUserDto {

    private Long id;
    private Integer status;
    private Integer price;
    private Integer paymentMethod;
    private Boolean paymentStatus;
    private Integer gender;
    private String bookingCode;
    private String phone;
    private String name;
    private String email ;
    private Date checkIn;
    private Date startDate;
    private Date endDate;
    private HotelDtoAuto hotelDtoAuto;
    private List<BookingDetailDto> listBookingDetail;
}
