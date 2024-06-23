package com.hotel.api.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueOfYearDto {

    private long revenue;
    private Integer month;
    private long countBooking;

}
