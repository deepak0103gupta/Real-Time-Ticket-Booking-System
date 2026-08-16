package com.eventhub.event_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.event_service.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    
}
