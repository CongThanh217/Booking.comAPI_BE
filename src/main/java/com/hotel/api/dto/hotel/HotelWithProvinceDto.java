package com.hotel.api.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelWithProvinceDto {

    private Long count;
    private Long provinceId;
    private String name;
}
