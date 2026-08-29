package com.eventhub.booking_service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventhub.booking_service.client.EventServiceClient;
import com.eventhub.booking_service.dto.HoldRequest;
import com.eventhub.booking_service.dto.HoldResponse;
import com.eventhub.booking_service.dto.SeatDetailsResponse;
import com.eventhub.booking_service.entity.Booking;
import com.eventhub.booking_service.entity.BookingSeat;
import com.eventhub.booking_service.entity.BookingStatus;
import com.eventhub.booking_service.exception.SeatUnavailableException;
import com.eventhub.booking_service.repository.BookingRepository;
import com.eventhub.booking_service.service.BookingService;
import com.eventhub.booking_service.service.SeatHoldService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final SeatHoldService seatHoldService;
    private final BookingRepository bookingRepository;
    private final EventServiceClient eventServiceClient;

    @Value("${booking.hold.duration-seconds}")
    private long holdDurationSeconds;

    // NOTE: price-per-seat is hardcoded here for now since Booking Service
    // hasn't called Event Service yet to fetch real seat prices. We'll wire
    // that REST call in next — for now this proves the locking logic works.
    

    @Override
    @Transactional
    public HoldResponse holdSeats(HoldRequest request) {

        // 1. Fetch real seat data from Event Service via Feign
        List<SeatDetailsResponse> eventSeats = eventServiceClient.getSeatsForEvent(request.getEventId());
        

        Map<Long, SeatDetailsResponse> seatMap = eventSeats.stream()
                .collect(Collectors.toMap(SeatDetailsResponse::getId, s -> s));

        // 2. Validate every requested seat exists and is AVAILABLE, before touching Redis at all
        for (Long seatId : request.getSeatIds()) {
            SeatDetailsResponse seat = seatMap.get(seatId);
            if (seat == null) {
                throw new SeatUnavailableException(
                        "Seat " + seatId + " does not exist for event " + request.getEventId());
            }
            if (!"AVAILABLE".equals(seat.getSeatStatus())) {
                throw new SeatUnavailableException(
                        "Seat " + seatId + " is already booked");
            }
        }

        // 3. Now attempt the Redis holds — same rollback logic as before
        List<Long> successfullyHeldSeatIds = new ArrayList<>();
        try {
            for (Long seatId : request.getSeatIds()) {
                boolean held = seatHoldService.tryHoldSeat(
                        request.getEventId(), seatId, request.getUserId());

                if (!held) {
                    rollbackHolds(request.getEventId(), successfullyHeldSeatIds);
                    throw new SeatUnavailableException(
                            "Seat " + seatId + " is no longer available");
                }
                successfullyHeldSeatIds.add(seatId);
            }

            // 4. Build booking using REAL prices from Event Service, not a placeholder
            BigDecimal totalAmount = request.getSeatIds().stream()
                    .map(seatId -> seatMap.get(seatId).getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Booking booking = Booking.builder()
                    .userId(request.getUserId())
                    .eventId(request.getEventId())
                    .status(BookingStatus.HELD)
                    .totalAmount(totalAmount)
                    .build();

            List<BookingSeat> bookingSeats = request.getSeatIds().stream()
                    .map(seatId -> BookingSeat.builder()
                            .booking(booking)
                            .seatId(seatId)
                            .price(seatMap.get(seatId).getPrice())
                            .build())
                    .collect(Collectors.toList());

            booking.setBookingSeats(bookingSeats);
            Booking saved = bookingRepository.save(booking);

            return toResponse(saved);

        } catch (SeatUnavailableException ex) {
            throw ex;
        } catch (Exception ex) {
            rollbackHolds(request.getEventId(), successfullyHeldSeatIds);
            throw ex;
        }
    }

    private void rollbackHolds(Long eventId, List<Long> seatIds) {
        for (Long seatId : seatIds) {
            seatHoldService.releaseHold(eventId, seatId);
        }
    }

    private HoldResponse toResponse(Booking booking) {
        List<Long> seatIds = booking.getBookingSeats().stream()
                .map(BookingSeat::getSeatId)
                .collect(Collectors.toList());

        return HoldResponse.builder()
                .bookingId(booking.getId())
                .eventId(booking.getEventId())
                .status(booking.getStatus())
                .seatIds(seatIds)
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .holdExpiresAt(booking.getCreatedAt().plusSeconds(holdDurationSeconds))
                .build();
    }
}
