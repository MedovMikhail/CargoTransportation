package com.example.cargoTransportation.entities.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "product_generator", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
    @Max(30000)
    private double weight;

    @ManyToOne(targetEntity = CompanyEntity.class)
    @JoinColumn(name="company_id")
    private CompanyEntity company;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "product")
    private List<RequestEntity> requests;
}
