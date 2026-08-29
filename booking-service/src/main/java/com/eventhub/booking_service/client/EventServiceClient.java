package com.eventhub.booking_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventhub.booking_service.dto.SeatDetailsResponse;

@FeignClient(name = "event-service", url = "${event-service.url}")
public interface EventServiceClient {
    @GetMapping("/api/v1/events/{eventId}/seats")
    List<SeatDetailsResponse> getSeatsForEvent(@PathVariable("eventId") Long eventId);
}
