package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_booking_detail")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BookingDetail extends Auditable<String>{

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    private Integer price;
    private String size;
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "number_of_bed")
    private Integer numberOfBed;
    private Boolean breakfast;
    private Boolean bar;
    private Boolean steamRoom;
    private Boolean wifi;
    @Column(name = "root_top")
    private Boolean rootTop;
    @Column(name = "air_condition")
    private Boolean airCondition;
    private Boolean pool;
    @Column(name = "hot_bath_room")
    private Boolean hotBathRoom;
    private Boolean kitchenette;
    private Boolean bacony;
    @Column(name = "sea_view")
    private Boolean seaview;
    @Column(name = "room_name")
    private String roomName;


}
