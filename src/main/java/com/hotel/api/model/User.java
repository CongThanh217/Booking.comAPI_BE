package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "db_user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends Auditable<String>{

    private Date birthday;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Integer point;
    private Integer memberShip;
    private Integer gender;
    @ManyToMany
    @JoinTable(name = "is_save",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =@JoinColumn(name = "hotel_id"))
    private List<Hotel> savedHotels = new ArrayList<>();
}
