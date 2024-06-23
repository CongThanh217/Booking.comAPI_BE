package com.hotel.api.dto.kindOfRoom;

import com.hotel.api.dto.emptyRoom.EmptyRoomDto;
import com.hotel.api.dto.images.ImagesDto;
import com.hotel.api.dto.images.ImagesForHotelDto;
import com.hotel.api.model.Images;
import lombok.Data;

import java.util.List;


@Data
public class KindOfRoomDto {

    private Long id;
    private Integer price;
    private String name;
    private Integer roomNumber;
    private String size;
    private Integer saleOff;
    private Integer numberOfPeople;
    private Integer numberOfBed;
    private Integer status;
    private List<EmptyRoomDto> roomDtoList;
    // su dung chung ImagesForHotelDto cho hotel và kind
    private List<ImagesForHotelDto> imagesList;
}
