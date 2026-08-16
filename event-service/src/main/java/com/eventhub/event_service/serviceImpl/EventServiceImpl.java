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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEvents'");
    }

    @Override
    public EventResponseDto getEventById(Long eventId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEventById'");
    }

    @Override
    public EventResponseDto cancelEvent(Long eventId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelEvent'");
    }
    
}
