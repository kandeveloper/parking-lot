package com.parkinglot.demo.parking.entity;

import com.parkinglot.demo.ParkingSlotType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "parking_slots")
public class ParkingSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Slot id is required")
    @Column(nullable = false, unique = true)
    private String slotId;

    @NotNull(message = "Slot type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingSlotType type;

    @Column(nullable = false)
    private boolean occupied = false;

    @ManyToOne
    @JoinColumn(name = "parking_floor_id")
    private ParkingFloorEntity floor;

    public ParkingSlotEntity() {
    }

    public ParkingSlotEntity(String slotId, ParkingSlotType type, ParkingFloorEntity floor) {
        this.slotId = slotId;
        this.type = type;
        this.floor = floor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public ParkingSlotType getType() {
        return type;
    }

    public void setType(ParkingSlotType type) {
        this.type = type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingFloorEntity getFloor() {
        return floor;
    }

    public void setFloor(ParkingFloorEntity floor) {
        this.floor = floor;
    }
}
