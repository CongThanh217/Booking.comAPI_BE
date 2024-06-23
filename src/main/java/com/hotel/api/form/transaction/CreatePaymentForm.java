package com.hotel.api.form.transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class CreatePaymentForm {

    @NotNull(message = "bookingId cant not be null")
    @ApiModelProperty(name = "bookingId", required = true)
    private Long bookingId;

//    @NotNull(message = "amount cant not be null")
//    @ApiModelProperty(name = "amount", required = true)
//    private Long amount;

    private String urlSuccess;
    private String urlCancel;
}
