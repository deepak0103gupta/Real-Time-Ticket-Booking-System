package com.eventhub.event_service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.entity.Event;
import com.eventhub.event_service.entity.EventStatus;
import com.eventhub.event_service.mapper.EventMapper;
import com.eventhub.event_service.repository.EventRepository;
import com.eventhub.event_service.repository.VenueRepository;
import com.eventhub.event_service.service.EventService;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;


    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.eventMapper = eventMapper;

    }

    @Override
    public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
        try{
            Event event = new Event();
            event = eventMapper.mapToEntity(eventRequestDto);
            event.setCreatedAt(LocalDateTime.now());
            event.setStatus(EventStatus.UPCOMING);
            event.setVenue(venueRepository.findById(eventRequestDto.getVenueId()).orElseThrow(() -> new RuntimeException("Venue not found with id: " + eventRequestDto.getVenueId())));

            eventRepository.save(event);
            return eventMapper.mapToResponseDto(event);
        }catch(Exception e){
            throw new RuntimeException("Error creating event: " + e.getMessage());
        }
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        try{
            List<Event> events = eventRepository.findAll();
            return events.stream()
                    .map(eventMapper::mapToResponseDto)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException("Error fetching events: " + e.getMessage());
        }
    }

    @Override
    public EventResponseDto getEventById(Long eventId) {
        try{
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
            return eventMapper.mapToResponseDto(event);
        }catch(Exception e){
            throw new RuntimeException("Error fetching event: " + e.getMessage());
        }
    }

    @Override
    public EventResponseDto cancelEvent(Long eventId) {
        try{
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
            event.setStatus(EventStatus.CANCELLED);
            eventRepository.save(event);
            return eventMapper.mapToResponseDto(event);
        }catch(Exception e){
            throw new RuntimeException("Error cancelling event: " + e.getMessage());
        }
    }
    
}
