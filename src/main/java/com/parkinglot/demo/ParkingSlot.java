package com.parkinglot.demo;

import java.util.Objects;

public class ParkingSlot {
    private final String slotId;
    private final ParkingSlotType slotType;
    private volatile boolean occupied;
    private volatile Vehicle vehicle;

    public ParkingSlot(String slotId, ParkingSlotType slotType) {
        this.slotId = Objects.requireNonNull(slotId, "Slot id is required");
        this.slotType = Objects.requireNonNull(slotType, "Slot type is required");
    }

    public synchronized boolean isOccupied() {
        return occupied;
    }

    public synchronized void occupy(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (!supportsVehicleType(vehicle.getType())) {
            throw new IllegalArgumentException("Slot type " + slotType + " does not support vehicle type " + vehicle.getType());
        }
        if (occupied) {
            throw new IllegalStateException("Slot " + slotId + " is already occupied");
        }
        this.vehicle = vehicle;
        this.occupied = true;
    }

    public synchronized void release() {
        this.vehicle = null;
        this.occupied = false;
    }

    public boolean supportsVehicleType(VehicleType vehicleType) {
        return slotType.name().equals(vehicleType.name());
    }

    public String getSlotId() {
        return slotId;
    }

    public ParkingSlotType getSlotType() {
        return slotType;
    }

    public synchronized Vehicle getVehicle() {
        return vehicle;
    }
}
