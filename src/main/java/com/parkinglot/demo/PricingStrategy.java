package com.parkinglot.demo;

import java.math.BigDecimal;
import java.time.Duration;

public interface PricingStrategy {
    BigDecimal calculateFee(VehicleType vehicleType, Duration duration);
}
