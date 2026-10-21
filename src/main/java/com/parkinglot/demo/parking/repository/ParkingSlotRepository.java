package com.parkinglot.demo.parking.repository;

import com.parkinglot.demo.ParkingSlotType;
import com.parkinglot.demo.parking.entity.ParkingSlotEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, Long> {
    List<ParkingSlotEntity> findByOccupiedFalse();
    List<ParkingSlotEntity> findByTypeAndOccupiedFalse(ParkingSlotType type);
    Optional<ParkingSlotEntity> findBySlotId(String slotId);
}
