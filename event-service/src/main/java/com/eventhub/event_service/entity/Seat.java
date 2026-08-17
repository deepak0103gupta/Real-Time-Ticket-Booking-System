package com.eventhub.event_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Event event;
    private String seatRow;
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    
    private float price;
    private SeatStatus seatStatus;
}
