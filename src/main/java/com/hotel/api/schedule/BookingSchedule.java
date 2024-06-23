package com.hotel.api.schedule;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.model.Booking;
import com.hotel.api.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class BookingSchedule {

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Ho_Chi_Minh") // Chạy lúc 0h0p hàng ngày theo giờ Việt Nam (GMT+7)
    public void updateBookingStatus() {
        Date now = new Date(); // Thời gian hiện tại
        Date cutoffDate = calculateCutoffDate(now, 2); // Ngày cắt giảm là ngày hiện tại trừ đi 2 ngày
        bookingRepository.updateBookingStatus(UserBaseConstant.STATUS_COMPLETED, cutoffDate);
    }

    private Date calculateCutoffDate(Date currentDate, int days) {
        LocalDateTime currentLocalDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime cutoffLocalDateTime = currentLocalDateTime.minusDays(days);
        return Date.from(cutoffLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
