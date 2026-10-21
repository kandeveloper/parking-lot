package com.parkinglot.demo.parking.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record ExitVehicleRequest(
        @NotNull(message = "Exit time is required") Instant exitTime
) {
}
