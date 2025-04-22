package com.example.cargoTransportation.entities.driver;

import com.example.cargoTransportation.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class DriverEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(targetEntity = UserEntity.class)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Date dateOfBirth;
    private boolean isDrive;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "driver")
    private List<VehicleEntity> vehicles;
}
