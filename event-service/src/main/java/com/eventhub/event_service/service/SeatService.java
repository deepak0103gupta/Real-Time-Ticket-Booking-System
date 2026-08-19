package com.eventhub.event_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.SeatResponse;


@Service
public interface SeatService {
    List<SeatResponse> getSeatsByEventId(Long eventId);
    SeatResponse markSeatAsBooked(Long eventId, Long seatId);
    SeatResponse releaseSeat(Long eventId, Long seatId);
}
