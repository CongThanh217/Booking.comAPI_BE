package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "db_kind_room")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class KindOfRoom extends Auditable<String>{

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private Integer price;
    private String name;
    @Column(name = "room_number")
    private Integer roomNumber;
    private String size;
    @Column(name = "sale_off")
    private Integer saleOff;
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    @Column(name = "number_of_bed")
    private Integer numberOfBed;


}
