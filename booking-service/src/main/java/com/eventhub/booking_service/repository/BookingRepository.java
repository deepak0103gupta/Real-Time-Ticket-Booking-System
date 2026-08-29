package com.eventhub.booking_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.booking_service.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
