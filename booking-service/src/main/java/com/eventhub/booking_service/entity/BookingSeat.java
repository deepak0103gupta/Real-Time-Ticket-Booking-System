package com.eventhub.booking_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class BookingSeat {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private Booking booking;

    private Long seatId;
    private BigDecimal price;
}
