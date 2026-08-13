package com.eventhub.event_service.dto;

import lombok.Data;

@Data
public class VenueResponseDto {
    private String name;
    private String city;
    private String address;
    private int totalCapacity;
}
