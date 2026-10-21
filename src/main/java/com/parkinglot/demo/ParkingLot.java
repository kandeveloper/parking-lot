package com.parkinglot.demo;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final PricingStrategy pricingStrategy;
    private final Map<String, ParkingTicket> tickets = new ConcurrentHashMap<>();
    private final ReentrantLock lotLock = new ReentrantLock();

    public ParkingLot(List<ParkingFloor> floors, PricingStrategy pricingStrategy) {
        this.floors = new ArrayList<>(Objects.requireNonNull(floors, "Floors list is required"));
        this.pricingStrategy = Objects.requireNonNull(pricingStrategy, "Pricing strategy is required");
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "Vehicle is required");

        lotLock.lock();
        try {
            ParkingSlot availableSlot = floors.stream()
                    .map(floor -> floor.findAvailableSlot(vehicle.getType()))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No available slot for vehicle type " + vehicle.getType()));

            availableSlot.occupy(vehicle);

            String ticketId = UUID.randomUUID().toString();
            ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, availableSlot, Instant.now());
            tickets.put(ticketId, ticket);
            return ticket;
        } finally {
            lotLock.unlock();
        }
    }

    public BigDecimal exitVehicle(String ticketId, Instant exitTime) {
        Objects.requireNonNull(ticketId, "Ticket ID is required");
        Objects.requireNonNull(exitTime, "Exit time is required");

        lotLock.lock();
        try {
            ParkingTicket ticket = tickets.get(ticketId);
            if (ticket == null) {
                throw new IllegalArgumentException("Ticket not found: " + ticketId);
            }

            Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
            BigDecimal fee = pricingStrategy.calculateFee(ticket.getVehicle().getType(), duration);

            ticket.getSlot().release();
            tickets.remove(ticketId);
            return fee;
        } finally {
            lotLock.unlock();
        }
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public List<ParkingTicket> getTickets() {
        return Collections.unmodifiableList(new ArrayList<>(tickets.values()));
    }

    public int getAvailableSlotsCount() {
        return (int) floors.stream()
                .flatMap(floor -> floor.getSlots().stream())
                .filter(slot -> !slot.isOccupied())
                .count();
    }
}
