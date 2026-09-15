package com.eventhub.booking_service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventhub.booking_service.dto.BookingResponse;
import com.eventhub.booking_service.entity.Booking;
import com.eventhub.booking_service.entity.BookingSeat;

@Component
public class BookingResponseMapper {
    
    public BookingResponse toBookingResponse(Booking booking) {
        List<Long> seatIds = booking.getBookingSeats().stream()
                .map(BookingSeat::getSeatId)
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .eventId(booking.getEventId())
                .userId(booking.getUserId())
                .status(booking.getStatus())
                .seatIds(seatIds)
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .confirmedAt(booking.getConfirmedAt())
                .build();
    }
}
