package com.eventhub.booking_service.service;

public interface SeatHoldService {
    boolean tryHoldSeat(Long eventId, Long seatId, Long userId);
    void releaseHold(Long eventId, Long seatId);
    boolean isHeldByUser(Long eventId, Long seatId, Long userId);
}
