package com.hotel.api.form.booking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CreateBookingForm {

    @NotNull(message = "hotelId cant not be null")
    @ApiModelProperty(name = "hotelId",required = true)
    private Long hotelId;
    private Integer paymentMethod;
    private Integer gender;
    private String phone;
    private String name;
    private String email;
    @ApiModelProperty(name = "checkIn")
    private Date checkIn;

    @NotNull(message = "endDate cant not be null")
    @ApiModelProperty(name = "startDate",required = true)
    private Date startDate;

    @NotNull(message = "endDate cant not be null")
    @ApiModelProperty(name = "endDate",required = true)
    private Date endDate;
    List<AddKindToBooking> kindToBookingList;
}
