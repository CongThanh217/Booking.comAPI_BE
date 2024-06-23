package com.hotel.api.repository;

import com.hotel.api.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long>, JpaSpecificationExecutor<Images> {

    List<Images> findAllByKindRoomId(Long id);

    @Query("SELECT i from Images i" +
            " JOIN i.kindRoom kr " +
            "join kr.hotel h" +
            " where h.id= :hotelId")
    List<Images> findAllImagesByHotelId(@Param("hotelId") Long hotelId);
}
