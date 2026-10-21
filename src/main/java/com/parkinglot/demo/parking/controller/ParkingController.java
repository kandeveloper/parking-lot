package com.parkinglot.demo.parking.controller;

import com.parkinglot.demo.parking.dto.ExitVehicleRequest;
import com.parkinglot.demo.parking.dto.ParkVehicleRequest;
import com.parkinglot.demo.parking.dto.ParkingTicketResponse;
import com.parkinglot.demo.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api")
@Tag(name = "Parking Lot API", description = "Parking operations")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/park")
    @Operation(summary = "Park a vehicle", description = "Assigns the first free slot matching the vehicle type")
    public ResponseEntity<ParkingTicketResponse> parkVehicle(@Valid @RequestBody ParkVehicleRequest request) {
        ParkingTicketResponse response = parkingService.parkVehicle(request.licensePlate(), request.vehicleType());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/exit/{ticketId}")
    @Operation(summary = "Exit a vehicle", description = "Calculates the parking fee and releases the slot")
    public ResponseEntity<ParkingTicketResponse> exitVehicle(@PathVariable String ticketId,
                                                            @Valid @RequestBody ExitVehicleRequest request) {
        ParkingTicketResponse response = parkingService.exitVehicle(ticketId, request.exitTime());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(summary = "Get parking status", description = "Returns current slot availability")
    public ResponseEntity<Map<String, Object>> getStatus() {
        return ResponseEntity.ok(Map.of(
                "availableSlots", parkingService.countAvailableSlots(),
                "totalSlots", parkingService.getAllSlots().size()
        ));
    }
}
