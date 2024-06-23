package com.hotel.api.form.booking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddKindToBooking {

    @NotNull(message = "kindOfRoomId cant not be null")
    @ApiModelProperty(name = "kindOfRoomId",required = true)
    private Long kindOfRoomId;

    @NotNull(message = "quantity cant not be null")
    @ApiModelProperty(name = "quantity",required = true)
    private Integer quantity;

    @NotNull(message = "emptyRoomId cant not be null")
    @ApiModelProperty(name = "emptyRoomId",required = true)
    private Long emptyRoomId;

}
