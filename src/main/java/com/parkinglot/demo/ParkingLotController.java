package com.parkinglot.demo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking")
public class ParkingLotController {

    private final ParkingLot parkingLot;

    public ParkingLotController(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @PostMapping("/park")
    public ResponseEntity<?> parkVehicle(@RequestBody VehicleRequest request) {
        try {
            Vehicle vehicle = new Vehicle(request.licensePlate(), request.vehicleType());
            ParkingTicket ticket = parkingLot.parkVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "ticketId", ticket.getTicketId(),
                    "slotId", ticket.getSlot().getSlotId(),
                    "entryTime", ticket.getEntryTime(),
                    "vehicle", Map.of(
                            "licensePlate", vehicle.getLicensePlate(),
                            "type", vehicle.getType().name()
                    )
            ));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/exit/{ticketId}")
    public ResponseEntity<?> exitVehicle(@PathVariable String ticketId, @RequestBody ExitRequest request) {
        try {
            BigDecimal fee = parkingLot.exitVehicle(ticketId, request.exitTime());
            return ResponseEntity.ok(Map.of("ticketId", ticketId, "fee", fee));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getParkingStatus() {
        return ResponseEntity.ok(Map.of(
                "availableSlots", parkingLot.getAvailableSlotsCount(),
                "tickets", parkingLot.getTickets().size()
        ));
    }

    public record VehicleRequest(String licensePlate, VehicleType vehicleType) {}
    public record ExitRequest(Instant exitTime) {}
}
