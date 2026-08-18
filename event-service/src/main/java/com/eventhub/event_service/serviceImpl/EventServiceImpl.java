package com.eventhub.event_service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.entity.Event;
import com.eventhub.event_service.entity.EventStatus;
import com.eventhub.event_service.entity.Seat;
import com.eventhub.event_service.entity.SeatStatus;
import com.eventhub.event_service.mapper.EventMapper;
import com.eventhub.event_service.repository.EventRepository;
import com.eventhub.event_service.repository.SeatRepository;
import com.eventhub.event_service.repository.VenueRepository;
import com.eventhub.event_service.service.EventService;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, VenueRepository venueRepository, SeatRepository seatRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.eventMapper = eventMapper;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
        try {
            Event event = new Event();
            event = eventMapper.mapToEntity(eventRequestDto);
            event.setCreatedAt(LocalDateTime.now());
            event.setStatus(EventStatus.UPCOMING);
            event.setVenue(venueRepository.findById(eventRequestDto.getVenueId()).orElseThrow(
                    () -> new RuntimeException("Venue not found with id: " + eventRequestDto.getVenueId())));

                    
                    Event savedEvent = eventRepository.save(event);
                    List<Seat> seats = eventMapper.maptoSeatEntity(savedEvent, eventRequestDto.getSeatLayout().getSeats());
                    seatRepository.saveAll(seats);
                    int totalSeats = seats.size();
                    long availableSeats = seats.size();
            return eventMapper.mapToResponseDto(event, totalSeats, availableSeats);
        } catch (Exception e) {
            throw new RuntimeException("Error creating event: " + e.getMessage());
        }
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            return events.stream()
                    .map(event -> {
                        int totalSeats = event.getSeats().size();
                        long availableSeats = event.getSeats().stream()
                                .filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE).count();
                        return eventMapper.mapToResponseDto(event, totalSeats, availableSeats);
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching events: " + e.getMessage());
        }
    }

    @Override
    public EventResponseDto getEventById(Long eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
            int totalSeats = event.getSeats().size();
            long availableSeats = event.getSeats().stream().filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE)
                    .count();
            return eventMapper.mapToResponseDto(event, totalSeats, availableSeats);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching event: " + e.getMessage());
        }
    }

    @Override
    public EventResponseDto cancelEvent(Long eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
            event.setStatus(EventStatus.CANCELLED);
            eventRepository.save(event);
            int totalSeats = event.getSeats().size();
            long availableSeats = event.getSeats().stream().filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE)
                    .count();
            return eventMapper.mapToResponseDto(event, totalSeats, availableSeats);
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling event: " + e.getMessage());
        }
    }

}
