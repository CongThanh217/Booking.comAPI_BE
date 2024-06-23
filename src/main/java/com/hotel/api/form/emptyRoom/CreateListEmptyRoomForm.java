package com.hotel.api.form.emptyRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateListEmptyRoomForm {

    @ApiModelProperty(name = "kind of room Id",required = true)
    private Long kindOfRoomId;
    @ApiModelProperty(name = "emptyRoom")
    private CreateOneEmptyRoomForm createOneEmptyRoomForm;
}
