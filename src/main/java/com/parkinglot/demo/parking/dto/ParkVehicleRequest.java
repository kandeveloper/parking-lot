package com.parkinglot.demo.parking.dto;

import com.parkinglot.demo.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkVehicleRequest(
        @NotBlank(message = "License plate is required") String licensePlate,
        @NotNull(message = "Vehicle type is required") VehicleType vehicleType
) {
}
