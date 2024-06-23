package com.hotel.api.dto.booking;

import lombok.Data;

@Data
public class BookingDetailDto {

    private Long id;
    private String roomName;
    private Integer price;
    private String size;
    private Integer roomNumber;
    private Integer numberOfBed;
    private Boolean breakfast;
    private Boolean bar;
    private Boolean steamRoom;
    private Boolean wifi;
    private Boolean rootTop;
    private Boolean airCondition;
    private Boolean pool;
    private Boolean hotBathRoom;
    private Boolean kitchenette;
    private Boolean bacony;
    private Boolean seaview;

}
