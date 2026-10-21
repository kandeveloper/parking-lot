package com.parkinglot.demo.parking.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ParkingTicketResponse(
        String ticketId,
        String licensePlate,
        String vehicleType,
        String slotId,
        Instant entryTime,
        BigDecimal fee,
        Instant exitTime
) {
}
