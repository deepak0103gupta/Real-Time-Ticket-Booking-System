package com.eventhub.booking_service.exception;

public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(String message){
        super(message);
    }
}
