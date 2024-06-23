package com.hotel.api.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomBookingCountDto {

    private Long count;
    private String roomName;
}
