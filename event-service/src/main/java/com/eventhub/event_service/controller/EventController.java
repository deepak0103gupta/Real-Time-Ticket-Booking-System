package com.eventhub.event_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.service.EventService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        EventResponseDto eventResponseDto = eventService.createEvent(eventRequestDto);
        return ResponseEntity.ok(eventResponseDto);
    }

    
    @GetMapping()
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        List<EventResponseDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long eventId) {
        EventResponseDto eventResponseDto = eventService.getEventById(eventId);
        return ResponseEntity.ok(eventResponseDto);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponseDto> cancelEvent(@PathVariable Long eventId) {
        EventResponseDto eventResponseDto = eventService.cancelEvent(eventId);
        return ResponseEntity.ok(eventResponseDto);
    }
}
