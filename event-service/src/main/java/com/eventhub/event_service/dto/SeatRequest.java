package com.eventhub.event_service.dto;

import com.eventhub.event_service.entity.SeatType;

import lombok.Data;

@Data
public class SeatRequest {
    private String rowLabel;
    private int seatsInRow;
    private String seatType; 
    private float price;
}
