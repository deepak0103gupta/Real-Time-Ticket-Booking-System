package com.eventhub.booking_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.eventhub.booking_service.dto.SeatDetailsResponse;

@FeignClient(name = "event-service", url = "${event-service.url}")
public interface EventServiceClient {
    @GetMapping("/api/v1/events/{eventId}/seats")
    List<SeatDetailsResponse> getSeatsForEvent(@PathVariable("eventId") Long eventId);

    @PutMapping("/api/v1/events/{eventId}/seats/{seatId}/book")
    SeatDetailsResponse markSeatBooked(@PathVariable("eventId") Long eventId, @PathVariable("seatId") Long seatId);

    @PutMapping("/api/v1/events/{eventId}/seats/{seatId}/release")
    SeatDetailsResponse releaseSeat(@PathVariable("eventId") Long eventId, @PathVariable("seatId") Long seatId);
}
