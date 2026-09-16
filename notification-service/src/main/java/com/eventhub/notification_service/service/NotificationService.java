package com.eventhub.notification_service.service;

import com.eventhub.notification_service.dto.BookingEvent;

public interface NotificationService {
    void sendBookingConfirmation(BookingEvent event);
    void sendBookingExpiredNotice(BookingEvent event);
    void sendGenericNotice(BookingEvent event);
}
