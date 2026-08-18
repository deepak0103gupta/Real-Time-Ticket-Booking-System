package com.eventhub.event_service.dto;

import com.eventhub.event_service.entity.SeatStatus;

import lombok.Data;

@Data
public class SeatResponse {
    private Long id;
    private String seatRow;
    private int seatNumber;
    private String seatType;
    private float price;
    private SeatStatus seatStatus;
}
