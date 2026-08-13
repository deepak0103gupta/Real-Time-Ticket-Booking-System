package com.eventhub.event_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.VenueRequestDto;
import com.eventhub.event_service.dto.VenueResponseDto;

@Service
public interface VenueService {
    public VenueResponseDto createVenue(VenueRequestDto venue);
    public List<VenueResponseDto> getAllVenues();
    public VenueResponseDto getVenueById(Long id);
}
