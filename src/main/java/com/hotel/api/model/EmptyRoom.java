package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "db_empty_room")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class EmptyRoom extends Auditable<String>{

    @Column(name = "empty_room")
    private Integer emptyRoom;
    @Column(name = "start_Date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "kind_of_room_id")
    private KindOfRoom kindOfRoom;
    @Column(name = "price")
    private Integer price;


}
