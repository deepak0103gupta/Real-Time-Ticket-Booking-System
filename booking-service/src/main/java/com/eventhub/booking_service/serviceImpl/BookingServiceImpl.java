package com.eventhub.booking_service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eventhub.booking_service.dto.HoldRequest;
import com.eventhub.booking_service.dto.HoldResponse;
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

    @Value("${booking.hold.duration-seconds}")
    private long holdDurationSeconds;

    // NOTE: price-per-seat is hardcoded here for now since Booking Service
    // hasn't called Event Service yet to fetch real seat prices. We'll wire
    // that REST call in next — for now this proves the locking logic works.
    private static final BigDecimal PLACEHOLDER_PRICE = BigDecimal.valueOf(1000);

    @Override
    @Transactional
    public HoldResponse holdSeats(HoldRequest request) {
        List<Long> successfullyHeldSeatIds = new ArrayList<>();

        try {
            for (Long seatId : request.getSeatIds()) {
                boolean held = seatHoldService.tryHoldSeat(
                        request.getEventId(), seatId, request.getUserId());

                if (!held) {
                    // Roll back every seat we already grabbed in this same request
                    // before throwing — otherwise a failed 3-seat booking would leave
                    // 2 seats stuck HELD in Redis for 5 minutes for nothing.
                    rollbackHolds(request.getEventId(), successfullyHeldSeatIds);
                    throw new SeatUnavailableException(
                            "Seat " + seatId + " is no longer available");
                }
                successfullyHeldSeatIds.add(seatId);
            }

            Booking booking = Booking.builder()
                    .userId(request.getUserId())
                    .eventId(request.getEventId())
                    .status(BookingStatus.HELD)
                    .totalAmount(PLACEHOLDER_PRICE.multiply(
                            BigDecimal.valueOf(request.getSeatIds().size())))
                    .build();

            List<BookingSeat> bookingSeats = request.getSeatIds().stream()
                    .map(seatId -> BookingSeat.builder()
                            .booking(booking)
                            .seatId(seatId)
                            .price(PLACEHOLDER_PRICE)
                            .build())
                    .collect(Collectors.toList());

            booking.setBookingSeats(bookingSeats);
            Booking saved = bookingRepository.save(booking);

            return toResponse(saved);

        } catch (SeatUnavailableException ex) {
            throw ex; // let GlobalExceptionHandler turn this into a 409
        } catch (Exception ex) {
            // Any unexpected failure after some holds were acquired — still roll back
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
