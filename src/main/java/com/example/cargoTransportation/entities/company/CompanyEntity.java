package com.example.cargoTransportation.entities.company;

import com.example.cargoTransportation.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CompanyEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(targetEntity = UserEntity.class)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private UserEntity user;

    @Column(unique = true, nullable = false, length = 50)
    private String name;
    private String description;
    @Column(nullable = false)
    private String industry;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "company")
    private List<ProductEntity> products;
}
