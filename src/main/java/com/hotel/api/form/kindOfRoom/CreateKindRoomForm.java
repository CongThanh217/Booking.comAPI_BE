package com.hotel.api.form.kindOfRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.mapping.List;

import javax.validation.constraints.NotNull;


@Data
public class CreateKindRoomForm {


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
    @ApiModelProperty(name = "numberOfBed")
    private Integer numberOfBed;
    @ApiModelProperty(name = "status",required = true)
    private Integer status;
    @ApiModelProperty(name = "images",required = true)
    private String[] images;

}
