package com.hotel.api.dto.hotel;

import com.hotel.api.dto.kindOfRoom.KindOfRoomDto;
import lombok.Data;

import java.util.List;

@Data
public class HotelFilterDto {
    private Long id;
    private String description;
    private String name;
    private String images;
    private Double totalReview;
    private Double stars;
    private String address;
    private Integer status;
    private String province;
    private String ward;
    private String district;
    private List<KindOfRoomDto> list;
}
