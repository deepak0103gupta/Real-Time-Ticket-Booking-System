package com.eventhub.event_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Venue {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String address;
    private int totalCapacity;
}
