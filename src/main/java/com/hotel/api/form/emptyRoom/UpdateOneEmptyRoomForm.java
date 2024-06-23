package com.hotel.api.form.emptyRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateOneEmptyRoomForm {

    @ApiModelProperty(name = "list emptyRoomId")
    private List<Long> emptyRoomId ;
    @ApiModelProperty(name = "number of room available",required = true)
    private Integer emptyRoomNumber;
    @ApiModelProperty(name = "price",required = true)
    private Integer price;
    @ApiModelProperty(name = "status",required = true)
    private Integer status;
}
