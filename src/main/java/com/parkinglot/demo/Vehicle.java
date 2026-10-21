package com.parkinglot.demo;

import java.util.Objects;

public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be blank");
        }
        this.licensePlate = licensePlate.trim();
        this.type = Objects.requireNonNull(type, "Vehicle type is required");
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) obj;
        return Objects.equals(licensePlate, vehicle.licensePlate) && type == vehicle.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, type);
    }
}
