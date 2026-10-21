package com.parkinglot.demo.parking.repository;

import com.parkinglot.demo.parking.entity.ParkingFloorEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloorEntity, Long> {
    Optional<ParkingFloorEntity> findByFloorId(String floorId);
}
