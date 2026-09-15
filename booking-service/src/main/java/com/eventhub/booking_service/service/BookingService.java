package com.eventhub.booking_service.service;

import com.eventhub.booking_service.dto.BookingResponse;
import com.eventhub.booking_service.dto.HoldRequest;
import com.eventhub.booking_service.dto.HoldResponse;

public interface BookingService {
    HoldResponse holdSeats(HoldRequest request);
    BookingResponse confirmBooking(Long bookingId);
}
