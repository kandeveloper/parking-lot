package com.parkinglot.demo;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ParkingLot parkingLot() {
        ParkingFloor groundFloor = new ParkingFloor("F1", List.of(
                new ParkingSlot("F1-A1", ParkingSlotType.CAR),
                new ParkingSlot("F1-A2", ParkingSlotType.CAR),
                new ParkingSlot("F1-B1", ParkingSlotType.BIKE),
                new ParkingSlot("F1-B2", ParkingSlotType.BIKE),
                new ParkingSlot("F1-C1", ParkingSlotType.TRUCK)
        ));

        ParkingFloor secondFloor = new ParkingFloor("F2", List.of(
                new ParkingSlot("F2-A1", ParkingSlotType.CAR),
                new ParkingSlot("F2-B1", ParkingSlotType.BIKE),
                new ParkingSlot("F2-C1", ParkingSlotType.TRUCK)
        ));

        return new ParkingLot(List.of(groundFloor, secondFloor), new HourlyPricingStrategy());
    }
}
