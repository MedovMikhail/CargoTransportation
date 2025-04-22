package com.example.cargoTransportation.entities;

import com.example.cargoTransportation.entities.driver.DriverEntity;
import com.example.cargoTransportation.utils.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private Long id;

    @Column(unique = true, nullable = false, length = 16)
    private String login;
    private String password;
    @Column(nullable = false)
    private UserType type;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
    private DriverEntity driver;
}
