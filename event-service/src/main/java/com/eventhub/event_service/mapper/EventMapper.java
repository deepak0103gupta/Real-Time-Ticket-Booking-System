package com.eventhub.event_service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.entity.Event;
import com.eventhub.event_service.serviceImpl.VenueServiceImpl;

@Component
public class EventMapper {


    
    public EventResponseDto mapToResponseDto(Event event) {
        // Mapping logic from Event entity to EventResponseDto
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setCategory(event.getCategory());
        dto.setEventDateTime(event.getEventDateTime());
        dto.setStatus(event.getStatus());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setVenue(event.getVenue());
        return dto;
    }

    public Event mapToEntity(EventRequestDto eventRequestDto) {
        // Mapping logic from EventRequestDto to Event entity
        Event event = new Event();

        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setCategory(eventRequestDto.getCategory());
        event.setEventDateTime(eventRequestDto.getEventDateTime());

        
        
        return event;
    }

}
