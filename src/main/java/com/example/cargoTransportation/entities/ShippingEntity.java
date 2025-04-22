package com.example.cargoTransportation.entities;

import com.example.cargoTransportation.entities.company.RequestEntity;
import com.example.cargoTransportation.entities.driver.VehicleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;

@Entity
@Getter
@Setter
public class ShippingEntity {

    @Id
    @SequenceGenerator(name = "shipping_generator", sequenceName = "shipping_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_generator")
    private Long id;

    @OneToOne(targetEntity = RequestEntity.class)
    @JoinColumn(name = "request_id")
    private RequestEntity request;

    @ManyToOne(targetEntity = VehicleEntity.class)
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    private boolean isComplete;
    private boolean tooLate;
    @Column(nullable = false)
    private Date startDate;
    private Date endDate;
}
