package com.hotel.api.dto.hotel;

import com.hotel.api.dto.nation.NationAdminDto;
import com.hotel.api.dto.nation.NationDtoPublic;
import com.hotel.api.dto.user.UserAutoCompleteDto;
import lombok.Data;

@Data
public class HotelDto {

    private Long id;
    private String description;
    private String name;
    private String images;
    private UserAutoCompleteDto userInfo;
    private Double totalReview;
    private Double stars;
    private String address;
    private Integer status;
    private NationDtoPublic provinceInfo;
    private NationDtoPublic districtInfo;
    private NationDtoPublic wardInfo;
    private Integer roomPrice;


}
