package com.eventhub.event_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.repository.EventRepository;

@Service
public interface EventService {
    public EventResponseDto createEvent(EventRequestDto eventRequestDto);
    public List<EventResponseDto> getAllEvents();
    public EventResponseDto getEventById(Long eventId);
    public EventResponseDto cancelEvent(Long eventId);
}
