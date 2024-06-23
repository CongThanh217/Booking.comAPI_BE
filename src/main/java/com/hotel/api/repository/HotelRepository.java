package com.hotel.api.repository;

import com.hotel.api.dto.hotel.HotelWithProvinceDto;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.KindOfRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Hotel findByUserId(long userId);

    @Query("SELECT new com.hotel.api.dto.hotel.HotelWithProvinceDto(COUNT(ht), pr.id, pr.name) " +
            "FROM Hotel ht JOIN ht.province pr " +
            "WHERE pr.kind = 1 " +
            "GROUP BY pr.id, pr.name")
    List<HotelWithProvinceDto> countHotelForProvince();

    @Query("SELECT DISTINCT h, min (e.price) FROM Hotel h " +
            "JOIN KindOfRoom k ON h.id = k.hotel.id " +
            "JOIN EmptyRoom e ON k.id = e.kindOfRoom.id " +
            "WHERE h.status = :status " +
            "AND k.status = :status " +
            "AND FUNCTION('DATE', e.startDate) >= FUNCTION('DATE', :startDate) " +
            "AND FUNCTION('DATE', e.endDate) <= FUNCTION('DATE', :endDate) " +
            "AND e.emptyRoom >= 1 " +
            "GROUP BY k.id " +
            "HAVING COUNT(e.id) = :daysCount")
    Page<Object[]> findHotelsWithEmptyRooms(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("daysCount") Long daysCount,
            @Param("status") Integer status, Pageable pageable);




}
