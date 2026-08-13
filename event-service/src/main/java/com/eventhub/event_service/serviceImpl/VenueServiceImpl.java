package com.eventhub.event_service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.VenueRequestDto;
import com.eventhub.event_service.dto.VenueResponseDto;
import com.eventhub.event_service.entity.Venue;
import com.eventhub.event_service.mapper.VenueMapper;
import com.eventhub.event_service.repository.VenueRepository;
import com.eventhub.event_service.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService{
    private VenueRepository venueRepo;
    private VenueMapper venueMapper;

    public VenueServiceImpl(VenueRepository venueRepository, VenueMapper venueMapper) {
        this.venueRepo = venueRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public VenueResponseDto createVenue(VenueRequestDto venue) {
        try{
            if(venue.getTotalCapacity() <= 0) {
                throw new IllegalArgumentException("Total capacity must be greater than zero.");
            }

            Venue venueEntity = venueMapper.mapToEntity(venue);
            Venue savedVenue = venueRepo.save(venueEntity);
            return venueMapper.mapToResponseDto(savedVenue);

            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating venue.", e);
        }

    }

    @Override
    public List<VenueResponseDto> getAllVenues() {
        // Implementation for retrieving all venues
        try {
            List<Venue> venues = venueRepo.findAll();
            return venues.stream()
                    .map(venueMapper::mapToResponseDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving venues.", e);
        }
    }

    @Override
    public VenueResponseDto getVenueById(Long id) {
        // Implementation for retrieving a venue by ID
        try {
            Venue venue = venueRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venue not found with ID: " + id));
            return venueMapper.mapToResponseDto(venue);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving venue by ID.", e);
        }
    }
}
