package com.parkinglot.demo.parking.repository;

import com.parkinglot.demo.parking.entity.ParkingTicketEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicketEntity, Long> {
    Optional<ParkingTicketEntity> findByTicketId(String ticketId);
}
