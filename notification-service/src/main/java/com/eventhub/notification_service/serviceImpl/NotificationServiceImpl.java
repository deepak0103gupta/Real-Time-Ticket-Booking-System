package com.eventhub.notification_service.serviceImpl;

import org.springframework.stereotype.Service;

import com.eventhub.notification_service.dto.BookingEvent;
import com.eventhub.notification_service.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service 
public class NotificationServiceImpl implements NotificationService{
    @Override
    public void sendBookingConfirmation(BookingEvent event) {
        log.info("✅ [EMAIL] Booking {} confirmed for user {} — seats {} — total ₹{}",
                event.getBookingId(), event.getUserId(), event.getSeatIds(), event.getTotalAmount());
    }

    @Override
    public void sendBookingExpiredNotice(BookingEvent event) {
        log.info("⌛ [EMAIL] Booking {} expired for user {} — seats {} released",
                event.getBookingId(), event.getUserId(), event.getSeatIds());
    }

    @Override
    public void sendGenericNotice(BookingEvent event) {
        log.info("ℹ️ [EMAIL] Unhandled event type {} for booking {}",
                event.getEventType(), event.getBookingId());
    }
}
