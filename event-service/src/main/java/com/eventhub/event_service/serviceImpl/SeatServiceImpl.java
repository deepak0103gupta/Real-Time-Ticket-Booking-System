package com.eventhub.event_service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eventhub.event_service.dto.SeatResponse;
import com.eventhub.event_service.entity.Seat;
import com.eventhub.event_service.mapper.SeatMapper;
import com.eventhub.event_service.repository.SeatRepository;
import com.eventhub.event_service.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    public SeatServiceImpl(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }
    @Override
    public List<SeatResponse> getSeatsByEventId(Long eventId) {
        
        return seatRepository.findByEventId(eventId)
                .stream()
                .map(seat -> seatMapper.toSeatResponse(seat))
                .collect(Collectors.toList());
    }


}
