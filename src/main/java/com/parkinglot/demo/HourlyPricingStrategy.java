package com.parkinglot.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Map;

public class HourlyPricingStrategy implements PricingStrategy {
    private final Map<VehicleType, BigDecimal> hourlyRates = Map.of(
            VehicleType.CAR, new BigDecimal("40.00"),
            VehicleType.BIKE, new BigDecimal("20.00"),
            VehicleType.TRUCK, new BigDecimal("80.00")
    );

    @Override
    public BigDecimal calculateFee(VehicleType vehicleType, Duration duration) {
        BigDecimal rate = hourlyRates.get(vehicleType);
        if (rate == null) {
            throw new IllegalArgumentException("No pricing defined for vehicle type: " + vehicleType);
        }

        long hours = duration.isZero() ? 1 : (long) Math.ceil(duration.toMinutes() / 60.0);
        return rate.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
    }
}
