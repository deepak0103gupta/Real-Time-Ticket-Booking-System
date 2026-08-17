package com.eventhub.event_service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventhub.event_service.dto.EventRequestDto;
import com.eventhub.event_service.dto.EventResponseDto;
import com.eventhub.event_service.dto.SeatRequest;
import com.eventhub.event_service.entity.Event;
import com.eventhub.event_service.entity.Seat;
import com.eventhub.event_service.entity.SeatStatus;
import com.eventhub.event_service.entity.SeatType;
import com.eventhub.event_service.serviceImpl.VenueServiceImpl;

@Component
public class EventMapper {

    public List<Seat> maptoSeatEntity( Event event,List<SeatRequest> seatRequests){
        
        List<Seat> seats = new ArrayList<>();
        for (SeatRequest seatRequest : seatRequests) {
            for(int i=1; i<= seatRequest.getSeatsInRow(); i++){
                Seat seat = new Seat();
                seat.setEvent(event);   
                seat.setSeatRow(seatRequest.getRowLabel());
                seat.setSeatNumber(i);
                seat.setSeatType(SeatType.valueOf(seatRequest.getSeatType()));
                seat.setPrice(seatRequest.getPrice());
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seats.add(seat);
            }
        }
        return seats;
    }

    public EventResponseDto mapToResponseDto(Event event,int totalSeats, long availableSeats ) {
        // Mapping logic from Event entity to EventResponseDto
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setCategory(event.getCategory());
        dto.setEventDateTime(event.getEventDateTime());
        dto.setStatus(event.getStatus());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setVenue(event.getVenue());
        dto.setTotalSeats(totalSeats);
        dto.setAvailableSeats(availableSeats);
        return dto;
    }

    public Event mapToEntity(EventRequestDto eventRequestDto) {
        // Mapping logic from EventRequestDto to Event entity
        Event event = new Event();

        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setCategory(eventRequestDto.getCategory());
        event.setEventDateTime(eventRequestDto.getEventDateTime());

        
        
        return event;
    }

}
