package com.eventhub.booking_service.exception;

public class BookingConflictException extends RuntimeException{
    public BookingConflictException(String message) {
        super(message);
    }    
}
