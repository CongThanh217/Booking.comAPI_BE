package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "db_hotel")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Hotel extends Auditable<String>{

    @Column(name = "description", columnDefinition = "text")
    private String description;

    private String name;
    private String images;

    @OneToOne
    @JoinColumn(name = "user_id" , nullable = true)
    private User user;

    @Column(name = "total_review")
    private Double totalReview;

    private Double stars;
    private String address;
    @ManyToOne
    private Nation province ;
    @ManyToOne
    private Nation district;
    @ManyToOne
    private Nation ward;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(getId(), hotel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
