package com.eventhub.event_service.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.event_service.dto.SeatResponse;
import com.eventhub.event_service.entity.Seat;

@Component
public class SeatMapper {
    public SeatResponse toSeatResponse(Seat seat) {
        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setId(seat.getId());
        seatResponse.setSeatRow(seat.getSeatRow());
        seatResponse.setSeatNumber(seat.getSeatNumber());
        seatResponse.setSeatType(seat.getSeatType().name());
        seatResponse.setPrice(seat.getPrice());
        seatResponse.setSeatStatus(seat.getSeatStatus());
        return seatResponse;
    }
}
