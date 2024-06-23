package com.hotel.api.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBookingDto {

    private Long totalCountBooking;
    private Long totalPriceBooking;
    private List<RoomBookingCountDto> roomBookingCountDto;
}
