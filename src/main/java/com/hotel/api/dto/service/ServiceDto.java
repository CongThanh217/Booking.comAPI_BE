package com.hotel.api.dto.service;

import com.hotel.api.dto.account.AccountDto;
import com.hotel.api.dto.ABasicAdminDto;
import com.hotel.api.model.Group;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceDto extends ABasicAdminDto {

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
    @ApiModelProperty(name = "hotBath Room")
    private boolean hotbathRoom;
    @ApiModelProperty(name = "kitchenette")
    private boolean kitchenette;
    @ApiModelProperty(name = "balcony")
    private boolean balcony;
    @ApiModelProperty(name = "seaView")
    private boolean seaView;
}
