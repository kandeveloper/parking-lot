package com.parkinglot.demo.parking.repository;

import com.parkinglot.demo.parking.entity.VehicleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
