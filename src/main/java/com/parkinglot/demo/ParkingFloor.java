package com.parkinglot.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ParkingFloor {
    private final String floorId;
    private final List<ParkingSlot> slots;

    public ParkingFloor(String floorId, List<ParkingSlot> slots) {
        this.floorId = Objects.requireNonNull(floorId, "Floor ID is required");
        this.slots = new ArrayList<>(Objects.requireNonNull(slots, "Slots list is required"));
    }

    public String getFloorId() {
        return floorId;
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public ParkingSlot findAvailableSlot(VehicleType vehicleType) {
        return slots.stream()
                .filter(slot -> !slot.isOccupied())
                .filter(slot -> slot.supportsVehicleType(vehicleType))
                .findFirst()
                .orElse(null);
    }
}
