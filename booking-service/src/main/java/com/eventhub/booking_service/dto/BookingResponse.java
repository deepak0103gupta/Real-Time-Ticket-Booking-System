package com.eventhub.booking_service.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.booking_service.entity.BookingStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long eventId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
}
