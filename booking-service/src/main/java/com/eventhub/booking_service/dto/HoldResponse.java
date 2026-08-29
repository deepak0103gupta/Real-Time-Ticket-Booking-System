package com.eventhub.booking_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.booking_service.entity.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldResponse {
    private Long bookingId;
    private Long eventId;
    private BookingStatus status;
    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime holdExpiresAt;
}
