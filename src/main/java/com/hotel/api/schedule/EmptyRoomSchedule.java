package com.hotel.api.schedule;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.repository.EmptyRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class EmptyRoomSchedule {

    @Autowired
    private EmptyRoomRepository emptyRoomRepository;

    @Scheduled(cron = "0 52 22 * * *", zone = "Asia/Ho_Chi_Minh") // Chạy lúc 0h0p hàng ngày theo giờ Việt Nam (GMT+7)
    public void updateBookingStatus() {
        Date now = new Date();
        Date cutoffDate = calculateCutoffDate(now, 15); //  ngày hiện tại trừ đi 15 ngày
        emptyRoomRepository.deleteEmptyOld(cutoffDate);
    }

    private Date calculateCutoffDate(Date currentDate, int days) {
        LocalDateTime currentLocalDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime cutoffLocalDateTime = currentLocalDateTime.minusDays(days);
        return Date.from(cutoffLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
