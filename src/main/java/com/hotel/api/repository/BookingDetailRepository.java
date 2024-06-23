package com.hotel.api.repository;

import com.hotel.api.model.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long>, JpaSpecificationExecutor<BookingDetail> {


    List<BookingDetail> findAllByBookingId(Long id);
}
