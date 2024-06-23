package com.hotel.api.repository;

import com.hotel.api.model.KindOfRoom;
import com.hotel.api.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long>, JpaSpecificationExecutor<Service> {

    @Query("select sv from Service sv " +
            "Join sv.kind_room svk " +
            "WHERE svk.id= :kind")
    Service findByKind_room(@Param("kind") long kind);
    Service findServicesByHotelId(long hotelId);

}
