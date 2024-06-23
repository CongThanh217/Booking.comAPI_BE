package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "db_booking")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Booking extends Auditable<String>{

    @OneToOne
    private Hotel hotel;

    private Integer price;

    @Column(name = "payment_method")
    private Integer paymentMethod;
    @Column(name = "payment_status")
    private Boolean paymentStatus;

    private Integer gender;
    @Column(name = "booking_Code")
    private String bookingCode;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    private String phone;
    private String name;
    @Email
    private String email;
    @Column(name = "check_in")
    private Date checkIn;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

}
