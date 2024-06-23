package com.hotel.api.form.kindOfRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateKindRoomForm {

    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @ApiModelProperty(name = "price",required = true)
    private Integer price;
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @ApiModelProperty(name = "roomNumber")
    private Integer roomNumber;
    @ApiModelProperty(name = "size")
    private String size;
    @ApiModelProperty(name = "saleOff")
    private Integer saleOff;
    @ApiModelProperty(name = "numberOfPeople")
    private Integer numberOfPeople;
    @ApiModelProperty(name = "numberOfBed",required = true)
    private Integer numberOfBed;
    @ApiModelProperty(name = "status",required = true)
    private Integer status;

}
