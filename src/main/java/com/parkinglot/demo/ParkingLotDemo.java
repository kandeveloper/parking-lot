package com.parkinglot.demo;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ParkingLotDemo {
    public static void main(String[] args) {
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

        ParkingLot parkingLot = new ParkingLot(List.of(groundFloor, secondFloor), new HourlyPricingStrategy());

        Vehicle car = new Vehicle("KA-01-AB-1234", VehicleType.CAR);
        Vehicle bike = new Vehicle("KA-05-CD-9876", VehicleType.BIKE);
        Vehicle truck = new Vehicle("TN-09-XY-4567", VehicleType.TRUCK);

        ParkingTicket carTicket = parkingLot.parkVehicle(car);
        ParkingTicket bikeTicket = parkingLot.parkVehicle(bike);
        ParkingTicket truckTicket = parkingLot.parkVehicle(truck);

        System.out.println("Car parked at: " + carTicket.getSlot().getSlotId());
        System.out.println("Bike parked at: " + bikeTicket.getSlot().getSlotId());
        System.out.println("Truck parked at: " + truckTicket.getSlot().getSlotId());

        Instant exitTime = Instant.now().plus(5, ChronoUnit.HOURS);
        BigDecimal carFee = parkingLot.exitVehicle(carTicket.getTicketId(), exitTime);
        BigDecimal bikeFee = parkingLot.exitVehicle(bikeTicket.getTicketId(), exitTime);
        BigDecimal truckFee = parkingLot.exitVehicle(truckTicket.getTicketId(), exitTime);

        System.out.println("Car fee: ₹" + carFee);
        System.out.println("Bike fee: ₹" + bikeFee);
        System.out.println("Truck fee: ₹" + truckFee);
    }
}
