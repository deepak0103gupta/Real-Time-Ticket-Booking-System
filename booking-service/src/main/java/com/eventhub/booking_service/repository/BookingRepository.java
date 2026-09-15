package com.eventhub.booking_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.booking_service.entity.Booking;
import com.eventhub.booking_service.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatusAndCreatedAtBefore(BookingStatus status, LocalDateTime cutoff);
}
