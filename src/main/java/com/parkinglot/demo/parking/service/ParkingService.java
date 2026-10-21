package com.parkinglot.demo.parking.service;

import com.parkinglot.demo.HourlyPricingStrategy;
import com.parkinglot.demo.ParkingSlotType;
import com.parkinglot.demo.Vehicle;
import com.parkinglot.demo.VehicleType;
import com.parkinglot.demo.parking.dto.ParkingTicketResponse;
import com.parkinglot.demo.parking.entity.ParkingFloorEntity;
import com.parkinglot.demo.parking.entity.ParkingSlotEntity;
import com.parkinglot.demo.parking.entity.ParkingTicketEntity;
import com.parkinglot.demo.parking.entity.VehicleEntity;
import com.parkinglot.demo.parking.repository.ParkingFloorRepository;
import com.parkinglot.demo.parking.repository.ParkingSlotRepository;
import com.parkinglot.demo.parking.repository.ParkingTicketRepository;
import com.parkinglot.demo.parking.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingTicketRepository parkingTicketRepository;
    private final ParkingFloorRepository parkingFloorRepository;
    private final HourlyPricingStrategy pricingStrategy = new HourlyPricingStrategy();

    public ParkingService(ParkingSlotRepository parkingSlotRepository,
                         VehicleRepository vehicleRepository,
                         ParkingTicketRepository parkingTicketRepository,
                         ParkingFloorRepository parkingFloorRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.parkingFloorRepository = parkingFloorRepository;
        initializeDefaultFloorsAndSlots();
    }

    @Transactional
    public ParkingTicketResponse parkVehicle(String licensePlate, VehicleType vehicleType) {
        String normalizedPlate = licensePlate == null ? null : licensePlate.trim();
        if (normalizedPlate == null || normalizedPlate.isBlank()) {
            throw new IllegalArgumentException("License plate is required");
        }

        VehicleEntity vehicle = vehicleRepository.findByLicensePlate(normalizedPlate)
                .orElseGet(() -> vehicleRepository.save(new VehicleEntity(normalizedPlate, vehicleType)));

        ParkingSlotEntity availableSlot = parkingSlotRepository.findByTypeAndOccupiedFalse(mapType(vehicleType))
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No available slot for vehicle type " + vehicleType));

        availableSlot.setOccupied(true);
        parkingSlotRepository.save(availableSlot);

        String ticketId = UUID.randomUUID().toString();
        ParkingTicketEntity ticket = new ParkingTicketEntity(ticketId, vehicle, availableSlot, Instant.now());
        ParkingTicketEntity saved = parkingTicketRepository.save(ticket);

        return new ParkingTicketResponse(
                saved.getTicketId(),
                vehicle.getLicensePlate(),
                vehicle.getType().name(),
                availableSlot.getSlotId(),
                saved.getEntryTime(),
                null,
                null
        );
    }

    @Transactional
    public ParkingTicketResponse exitVehicle(String ticketId, Instant exitTime) {
        ParkingTicketEntity ticket = parkingTicketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + ticketId));

        if (ticket.getExitTime() != null) {
            throw new IllegalStateException("Ticket already closed: " + ticketId);
        }

        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        BigDecimal fee = pricingStrategy.calculateFee(ticket.getVehicle().getType(), duration);

        ParkingSlotEntity slot = ticket.getSlot();
        slot.setOccupied(false);
        parkingSlotRepository.save(slot);

        ticket.setExitTime(exitTime);
        ticket.setFee(fee);
        ParkingTicketEntity saved = parkingTicketRepository.save(ticket);

        return new ParkingTicketResponse(
                saved.getTicketId(),
                saved.getVehicle().getLicensePlate(),
                saved.getVehicle().getType().name(),
                slot.getSlotId(),
                saved.getEntryTime(),
                saved.getFee(),
                saved.getExitTime()
        );
    }

    public List<ParkingSlotEntity> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public long countAvailableSlots() {
        return parkingSlotRepository.findByOccupiedFalse().size();
    }

    private void initializeDefaultFloorsAndSlots() {
        if (parkingFloorRepository.count() > 0) {
            return;
        }

        ParkingFloorEntity floor1 = parkingFloorRepository.save(new ParkingFloorEntity("F1"));
        ParkingFloorEntity floor2 = parkingFloorRepository.save(new ParkingFloorEntity("F2"));

        parkingSlotRepository.save(new ParkingSlotEntity("F1-A1", ParkingSlotType.CAR, floor1));
        parkingSlotRepository.save(new ParkingSlotEntity("F1-A2", ParkingSlotType.CAR, floor1));
        parkingSlotRepository.save(new ParkingSlotEntity("F1-B1", ParkingSlotType.BIKE, floor1));
        parkingSlotRepository.save(new ParkingSlotEntity("F1-B2", ParkingSlotType.BIKE, floor1));
        parkingSlotRepository.save(new ParkingSlotEntity("F1-C1", ParkingSlotType.TRUCK, floor1));

        parkingSlotRepository.save(new ParkingSlotEntity("F2-A1", ParkingSlotType.CAR, floor2));
        parkingSlotRepository.save(new ParkingSlotEntity("F2-B1", ParkingSlotType.BIKE, floor2));
        parkingSlotRepository.save(new ParkingSlotEntity("F2-C1", ParkingSlotType.TRUCK, floor2));
    }

    private ParkingSlotType mapType(VehicleType vehicleType) {
        return switch (vehicleType) {
            case CAR -> ParkingSlotType.CAR;
            case BIKE -> ParkingSlotType.BIKE;
            case TRUCK -> ParkingSlotType.TRUCK;
        };
    }
}
