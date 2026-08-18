package com.eventhub.event_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.event_service.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{
    List<Seat> findByEventId(Long eventId);
    
}
