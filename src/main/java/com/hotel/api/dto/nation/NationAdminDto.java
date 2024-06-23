package com.hotel.api.dto.nation;

import com.hotel.api.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class NationAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "postCode")
    private String postCode;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "parent")
    private NationDto parent;
}
