package com.hotel.api.service;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.statistical.RevenueOfYearDto;
import com.hotel.api.dto.statistical.TotalBookingDto;

import java.util.Date;
import java.util.List;

public interface StatisticalService {

    ApiMessageDto<TotalBookingDto> getStatiticOfBooking(Integer status , Long accountId, Integer month,Integer year);

    ApiMessageDto<List<RevenueOfYearDto>> getRevenueMonth(Integer year,Long account);
}
