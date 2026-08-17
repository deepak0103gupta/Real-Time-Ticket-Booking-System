package com.eventhub.event_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeatLayoutRequest {
    List<SeatRequest> seats;
}
