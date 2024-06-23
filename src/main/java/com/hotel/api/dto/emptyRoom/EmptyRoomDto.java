package com.hotel.api.dto.emptyRoom;

import lombok.Data;

import java.util.Date;

@Data
public class EmptyRoomDto {

    private Long emptyRoomId;
    private Date startDate;
    private Date endDate;
    private Integer emptyRoom;
    private Integer price;
    private Integer status;

}
