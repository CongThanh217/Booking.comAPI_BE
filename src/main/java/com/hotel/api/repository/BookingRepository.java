package com.hotel.api.repository;

import com.hotel.api.dto.statistical.RevenueOfYearDto;
import com.hotel.api.dto.statistical.RoomBookingCountDto;
import com.hotel.api.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {


    Page<Booking> findAllByUserId(Long userId , Pageable pageable);
    Booking findBookingByBookingCode(String code);

    @Query("SELECT new com.hotel.api.dto.statistical.RoomBookingCountDto(count(bookD), bookD.roomName) " +
            "FROM BookingDetail bookD join bookD.booking book" +
            " WHERE (:status IS NULL OR book.status= :status) AND" +
            " book.hotel.id = :hotelId" +
            " AND (:month IS NULL OR MONTH(book.startDate) = :month)" +
            " AND (:year IS NULL OR YEAR (book.startDate) = :year)" +
            " group by bookD.roomName")
    List<RoomBookingCountDto> roomBookingCount(@Param("status")Integer status, @Param("hotelId") Long hotelId, @Param("month") Integer month,@Param("year") Integer year);


    @Query("select count (bk) FROM " +
            "Booking bk " +
            "WHERE bk.hotel.id = :hotelId AND " +
            "(:status IS NULL OR bk.status= :status) AND " +
            "(:month IS NULL OR MONTH(bk.startDate) = :month)" +
            " AND (:year IS NULL OR YEAR (bk.startDate) = :year)")
    Long countBookingByHotelAndStatus(@Param("status")Integer status, @Param("hotelId") Long hotelId,  @Param("month") Integer month,@Param("year") Integer year);

    @Query("select sum (bk.price) FROM " +
            "Booking bk " +
            "WHERE bk.hotel.id = :hotelId AND " +
            "(:status IS NULL OR bk.status= :status) AND " +
            "(:month IS NULL OR MONTH(bk.startDate) = :month)" +
            " AND (:year IS NULL OR YEAR (bk.startDate) = :year)")
    Long totalPriceBooking(@Param("status")Integer status, @Param("hotelId") Long hotelId,  @Param("month") Integer month,@Param("year") Integer year);

    @Query("select CASE WHEN count (bk) > 0 THEN TRUE ELSE FALSE END" +
            " FROM Booking bk" +
            " WHERE (bk.startDate BETWEEN :startDate AND  :endDate)")
    boolean checkRoom(@Param("startDate") Date startDate , @Param("endDate") Date endDate);

    @Transactional
    @Modifying
    @Query("UPDATE Booking bk SET bk.status = :statusCompleted WHERE bk.status = 1 AND bk.startDate <= :cutoffDate")
    void updateBookingStatus(@Param("statusCompleted") int statusCompleted, @Param("cutoffDate") Date cutoffDate);

    @Query("SELECT NEW com.hotel.api.dto.statistical.RevenueOfYearDto(SUM(bk.price), MONTH(bk.startDate),count (bk.id)) " +
            "FROM Booking bk " +
            "WHERE bk.status = :status " +
            "AND bk.hotel.id = :hotelId " +
            "AND YEAR(bk.startDate) = :year " +
            "GROUP BY MONTH(bk.startDate)")
    List<RevenueOfYearDto> calculateYearRevenue(@Param("status") Integer status, @Param("year") Integer year, @Param("hotelId") Long hotelId);
}
