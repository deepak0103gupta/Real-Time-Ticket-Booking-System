package com.eventhub.event_service.dto;

import java.time.LocalDateTime;

import com.eventhub.event_service.entity.EventStatus;

import lombok.Data;

@Data
public class EventRequestDto {
    private String name;
    private String description;
    private String category;
    private LocalDateTime eventDateTime;
    private EventStatus status; 
    private LocalDateTime createdAt; 
    private Long venueId;  
}
