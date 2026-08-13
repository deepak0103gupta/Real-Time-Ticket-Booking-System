package com.eventhub.event_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.event_service.entity.Venue;



@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    
}
