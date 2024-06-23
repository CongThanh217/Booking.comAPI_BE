package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_images")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Images extends Auditable<String> {

    private String link;
    @ManyToOne
    @JoinColumn(name = "kind_room_id")
    private KindOfRoom kindRoom;
}
