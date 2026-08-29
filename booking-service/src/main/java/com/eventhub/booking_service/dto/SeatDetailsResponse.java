package com.eventhub.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDetailsResponse {
    private Long id;
    private String seatRow;
    private Integer seatNumber;
    private String seatType;
    private BigDecimal price;
    private String seatStatus;  
}