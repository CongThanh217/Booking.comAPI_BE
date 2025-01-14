package com.hotel.api.dto.address;

import com.hotel.api.dto.nation.NationDto;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String address;
    private NationDto wardInfo;
    private NationDto districtInfo;
    private NationDto provinceInfo;
    private String name;
    private String phone;
    private Integer status;
}
