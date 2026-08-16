package com.eventhub.event_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String description;
    private String category;
    @NotNull
    private LocalDateTime eventDateTime;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @ManyToOne
    private Venue venue;

    @OneToMany(mappedBy = "event")
    List<Seat> seats;

    private LocalDateTime createdAt;

}
