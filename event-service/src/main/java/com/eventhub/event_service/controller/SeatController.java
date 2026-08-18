package com.eventhub.event_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event_service.dto.SeatResponse;
import com.eventhub.event_service.service.SeatService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



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
    
}
