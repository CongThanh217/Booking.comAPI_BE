package com.hotel.api.form.emptyRoom;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CreateOneEmptyRoomForm {

    @ApiModelProperty(name = "number of room available")
    private Integer emptyRoom;
    @ApiModelProperty(name = "start date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @ApiModelProperty(name = "end date")
    private Date endDate;
    @ApiModelProperty(name = "price",required = true)
    private Integer price;
    @ApiModelProperty(name = "status",required = true)
    private Integer status;

}
