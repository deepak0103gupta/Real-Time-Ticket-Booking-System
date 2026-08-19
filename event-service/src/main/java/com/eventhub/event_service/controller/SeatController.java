package com.eventhub.event_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event_service.dto.SeatResponse;
import com.eventhub.event_service.service.SeatService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/v1/events/{eventId}/seats")
public class SeatController {
    private final SeatService seatService;
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping()
    public ResponseEntity<List<SeatResponse>> getAllSeats(@PathVariable Long eventId) {
        return ResponseEntity.ok(seatService.getSeatsByEventId(eventId));
    }

    @PutMapping("/{seatId}/book")
    public ResponseEntity<SeatResponse> bookSeats(@PathVariable Long eventId, @PathVariable Long seatId) {
        return ResponseEntity.ok(seatService.markSeatAsBooked(eventId, seatId));
    }

    @PutMapping("/{seatId}/release")
    public ResponseEntity<SeatResponse> releaseSeats(@PathVariable Long eventId, @PathVariable Long seatId) {
        return ResponseEntity.ok(seatService.releaseSeat(eventId, seatId));
    }
    
}
