package com.eventhub.booking_service.dto;

import java.util.List;

import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HoldRequest {
    @NotNull()
    private Long eventId;

    @NotNull()
    private Long userId;   // later this comes from the JWT, not the request body — see note below

    @NotEmpty(message = "At least one seatId is required")
    private List<Long> seatIds;
}
