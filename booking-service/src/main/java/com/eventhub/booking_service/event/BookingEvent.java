package com.eventhub.booking_service.event;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.booking_service.entity.BookingStatus;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BookingEvent {
    private String eventType;      // "BOOKING_CONFIRMED", "BOOKING_EXPIRED"
    private Long bookingId;
    private Long eventId;
    private Long userId;
    private List<Long> seatIds;
    private BookingStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime timestamp;
}
