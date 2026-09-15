package com.eventhub.booking_service.service;

import com.eventhub.booking_service.event.BookingEvent;

public interface BookingEventPublisher {
    void publish(BookingEvent event);
}
