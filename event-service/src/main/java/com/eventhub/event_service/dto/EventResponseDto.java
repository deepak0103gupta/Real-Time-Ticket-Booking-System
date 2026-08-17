package com.eventhub.event_service.dto;

import java.time.LocalDateTime;

import com.eventhub.event_service.entity.EventStatus;
import com.eventhub.event_service.entity.Venue;

import lombok.Data;

@Data
public class EventResponseDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private LocalDateTime eventDateTime;
    private EventStatus status; 
    private LocalDateTime createdAt; 
    private Venue venue;  
    private int totalSeats;
    private long availableSeats;
}


