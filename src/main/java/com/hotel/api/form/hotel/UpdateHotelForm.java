package com.hotel.api.form.hotel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateHotelForm {

    @NotNull(message = "id cant not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "name cant not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @NotEmpty(message = "description cant not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @NotEmpty(message = "images cant not be null")
    @ApiModelProperty(name = "images", required = true)
    private String images;
    @ApiModelProperty(name = "star")
    private Double star;
    @NotEmpty(message = "address cant not be empty")
    @ApiModelProperty(name = "address",required = true)
    private String address;
    @NotNull(message = "address cant not be empty")
    @ApiModelProperty(name = "provinceId " ,required = true)
    private Long provinceId;
    @NotNull(message = "address cant not be empty")
    @ApiModelProperty(name = "wardId ",required = true)
    private Long wardId;
    @NotNull(message = "address cant not be empty")
    @ApiModelProperty(name = "districtId ",required = true)
    private Long districtId;
    @NotNull(message = "status cant not be empty")
    @ApiModelProperty(name = "status",required = true)
    private Integer status;

}
