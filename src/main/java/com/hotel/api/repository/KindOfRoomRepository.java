package com.hotel.api.repository;

import com.hotel.api.model.KindOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface KindOfRoomRepository extends JpaRepository<KindOfRoom, Long>, JpaSpecificationExecutor<KindOfRoom> {
    List<KindOfRoom> findByHotelId(Long id);
    List<KindOfRoom> findAllByStatusAndHotelId(Integer status ,Long id);
    @Query("SELECT k FROM KindOfRoom k " +
            "JOIN EmptyRoom e ON k.id = e.kindOfRoom.id " +
            "WHERE k.status= :status AND " +
            "k.hotel.id = :hotelId " +
            "AND FUNCTION('DATE', e.startDate) >= FUNCTION('DATE', :startDate) " +
            "AND FUNCTION('DATE', e.endDate) <= FUNCTION('DATE', :endDate) " +
            "AND e.emptyRoom >= 1 " +
            "GROUP BY k.id " +
            "HAVING COUNT(e.id) = :daysCount")
    List<KindOfRoom> findKindsWithEmptyRooms(
            @Param("hotelId") Long hotelId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("daysCount") Long daysCount,
            @Param("status") Integer status);



}
