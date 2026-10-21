package com.parkinglot.demo.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "parking_tickets")
public class ParkingTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticketId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlotEntity slot;

    @Column(nullable = false)
    private Instant entryTime;

    @Column
    private Instant exitTime;

    @Column
    private java.math.BigDecimal fee;

    public ParkingTicketEntity() {
    }

    public ParkingTicketEntity(String ticketId, VehicleEntity vehicle, ParkingSlotEntity slot, Instant entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlotEntity getSlot() {
        return slot;
    }

    public void setSlot(ParkingSlotEntity slot) {
        this.slot = slot;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Instant entryTime) {
        this.entryTime = entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }

    public java.math.BigDecimal getFee() {
        return fee;
    }

    public void setFee(java.math.BigDecimal fee) {
        this.fee = fee;
    }
}
