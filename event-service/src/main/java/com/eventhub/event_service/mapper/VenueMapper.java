package com.eventhub.event_service.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.event_service.dto.VenueRequestDto;
import com.eventhub.event_service.dto.VenueResponseDto;
import com.eventhub.event_service.entity.Venue;

@Component
public class VenueMapper {
    
    public Venue mapToEntity(VenueRequestDto venueRequestDto) {
        // Mapping logic from VenueRequestDto to Venue entity
        Venue v = new Venue();
        v.setName(venueRequestDto.getName());
        v.setCity(venueRequestDto.getCity());
        v.setAddress(venueRequestDto.getAddress());
        v.setTotalCapacity(venueRequestDto.getTotalCapacity());
        return v;
    }

    public VenueResponseDto mapToResponseDto(Venue venue) {
        // Mapping logic from Venue entity to VenueResponseDto
        VenueResponseDto dto = new VenueResponseDto();
        dto.setName(venue.getName());
        dto.setCity(venue.getCity());
        dto.setAddress(venue.getAddress());
        dto.setTotalCapacity(venue.getTotalCapacity());
        return dto;
    }
}
