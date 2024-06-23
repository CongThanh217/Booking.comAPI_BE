package com.hotel.api.form.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CreateServiceHotelForm {
    @NotNull(message = "id hotel of room cant not be null")
    @ApiModelProperty(name = "hotelId", required = true)
    private Long hotelId;
    @ApiModelProperty(name = "breakfast")
    private boolean breakfast;
    @ApiModelProperty(name = "bar")
    private boolean bar;
    @ApiModelProperty(name = "steam room")
    private boolean steamRoom;
    @ApiModelProperty(name = "wifi")
    private boolean wifi;
    @ApiModelProperty(name = "rooftop")
    private boolean rooftop;
    @ApiModelProperty(name = "air condition")
    private boolean airCondition;
    @ApiModelProperty(name = "pool")
    private boolean pool;
    @ApiModelProperty(name = "hot BathRoom")
    private boolean hotbathRoom;
    @ApiModelProperty(name = "kitchenette")
    private boolean kitchenette;
    @ApiModelProperty(name = "balcony")
    private boolean balcony;
    @ApiModelProperty(name = "seaView")
    private boolean seaView;
}
