package com.hotel.api.repository;


import com.hotel.api.model.EmptyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface EmptyRoomRepository extends JpaRepository<EmptyRoom, Long>, JpaSpecificationExecutor<EmptyRoom> {

    List<EmptyRoom> findAllByKindOfRoomId(Long id);
    List<EmptyRoom> findAllByKindOfRoomIdAndStatusAndEmptyRoomGreaterThan(Long id,Integer status,Integer emptyRoom);

    @Query("SELECT e FROM EmptyRoom e " +
            "WHERE e.kindOfRoom.id = :kindId " +
            "AND DATE(e.startDate) >= DATE(:startDate) " +
            "AND DATE(e.endDate) <= DATE(:endDate)" +
            "AND e.status = :status ")
    List<EmptyRoom> findEmptyRoomsBetweenDates(
            @Param("kindId") Long kindId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("status") Integer status);

    @Query("SELECT e from EmptyRoom e WHERE DAY(e.startDate) = :day AND e.kindOfRoom.id = :kindId")
    EmptyRoom findByStartDate(@Param("day") int day ,@Param("kindId") Long kindId);

    @Transactional
    @Modifying
    @Query("delete EmptyRoom emt WHERE emt.startDate <= :cutoffDate")
    void deleteEmptyOld(@Param("cutoffDate") Date cutoffDate);
}
