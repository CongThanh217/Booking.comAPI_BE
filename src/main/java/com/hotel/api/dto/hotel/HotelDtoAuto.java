package com.hotel.api.dto.hotel;

import com.hotel.api.dto.nation.NationDtoPublic;
import lombok.Data;

@Data
public class HotelDtoAuto {

    private Long id;
    private String description;
    private String name;
    private String address;
    private NationDtoPublic provinceInfo;
    private NationDtoPublic districtInfo;
    private NationDtoPublic wardInfo;
}
