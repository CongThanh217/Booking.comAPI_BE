package com.hotel.api.dto.images;

import com.hotel.api.dto.kindOfRoom.KindOfRoomDtoAuto;
import lombok.Data;

@Data
public class ImagesDto {

    private long id;
    private String link;
    private KindOfRoomDtoAuto kindRoom;
}
