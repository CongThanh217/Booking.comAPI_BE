package com.hotel.api.form.kindOfRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangeStatusKindForm {

    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @ApiModelProperty(name = "status",required = true)
    private Integer status;
}
