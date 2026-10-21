package com.parkinglot.demo;

import java.time.Instant;
import java.util.Objects;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final Instant entryTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSlot slot, Instant entryTime) {
        this.ticketId = Objects.requireNonNull(ticketId, "Ticket ID is required");
        this.vehicle = Objects.requireNonNull(vehicle, "Vehicle is required");
        this.slot = Objects.requireNonNull(slot, "Parking slot is required");
        this.entryTime = Objects.requireNonNull(entryTime, "Entry time is required");
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public Instant getEntryTime() {
        return entryTime;
    }
}
