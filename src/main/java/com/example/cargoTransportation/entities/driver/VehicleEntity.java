package com.example.cargoTransportation.entities.driver;

import com.example.cargoTransportation.entities.ShippingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class VehicleEntity {

    @Id
    @SequenceGenerator(name = "vehicle_generator", sequenceName = "vehicle_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(500)
    @Max(500000)
    private double liftWeight;

    @ManyToOne(targetEntity = DriverEntity.class)
    @JoinColumn(name="driver_id")
    private DriverEntity driver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<ShippingEntity> shipping;
}
