package com.eventhub.event_service.dto;

import lombok.Data;

@Data
public class VenueRequestDto {
    private String name;
    private String city;
    private String address;
    private int totalCapacity;
}
