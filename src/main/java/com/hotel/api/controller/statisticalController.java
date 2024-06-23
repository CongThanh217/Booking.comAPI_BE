package com.hotel.api.controller;

import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.statistical.RevenueOfYearDto;
import com.hotel.api.dto.statistical.TotalBookingDto;
import com.hotel.api.service.impl.StatisticalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/statistical")
public class statisticalController extends ABasicController{
    @Autowired
    private StatisticalServiceImpl statisticalService;

    @GetMapping(value = "/statitics-booking", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('STA_L')")
    public ApiMessageDto<TotalBookingDto> getStatiticOfBooking(@RequestParam(value = "status" ,required = false) Integer status ,@RequestParam(value = "month",required = false) Integer month,@RequestParam(value = "year",required = false) Integer year) {

        Long account = getCurrentUser();
        ApiMessageDto<TotalBookingDto> apiMessageDto= statisticalService.getStatiticOfBooking(status,account,month,year);
        return apiMessageDto;
    }
    @GetMapping(value = "/get-revenue-month", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('REV_GRM')")
    public ApiMessageDto<List<RevenueOfYearDto>> getRevenueMonth(@RequestParam Integer year ) {
        Long account = getCurrentUser();
        ApiMessageDto<List<RevenueOfYearDto>> apiMessageDto=  statisticalService.getRevenueMonth(year,account);

        return apiMessageDto;
    }
}
