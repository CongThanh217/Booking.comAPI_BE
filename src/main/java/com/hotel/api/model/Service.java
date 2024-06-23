package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_service")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Service extends Auditable<String> {

    @OneToOne
    @JoinColumn(name = "kind_room")
    private KindOfRoom kind_room;
    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
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
}
