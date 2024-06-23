package com.hotel.api.dto.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NationDtoPublic {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
}
