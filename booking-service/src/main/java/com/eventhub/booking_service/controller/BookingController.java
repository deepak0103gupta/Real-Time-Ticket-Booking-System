package com.eventhub.booking_service.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.booking_service.dto.BookingResponse;
import com.eventhub.booking_service.dto.HoldRequest;
import com.eventhub.booking_service.dto.HoldResponse;
import com.eventhub.booking_service.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/hold")
    public ResponseEntity<HoldResponse> holdSeats(@Valid @RequestBody HoldRequest request) {
        HoldResponse response = bookingService.holdSeats(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long bookingId) {
        BookingResponse response = bookingService.confirmBooking(bookingId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
