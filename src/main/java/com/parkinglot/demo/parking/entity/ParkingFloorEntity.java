package com.parkinglot.demo.parking.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_floors")
public class ParkingFloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Floor id is required")
    @Column(nullable = false, unique = true)
    private String floorId;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSlotEntity> slots = new ArrayList<>();

    public ParkingFloorEntity() {
    }

    public ParkingFloorEntity(String floorId) {
        this.floorId = floorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public List<ParkingSlotEntity> getSlots() {
        return slots;
    }

    public void setSlots(List<ParkingSlotEntity> slots) {
        this.slots = slots;
    }
}
