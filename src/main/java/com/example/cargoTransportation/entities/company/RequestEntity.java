package com.example.cargoTransportation.entities.company;

import com.example.cargoTransportation.entities.ShippingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class RequestEntity {

    @Id
    @SequenceGenerator(name = "request_generator", sequenceName = "request_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_generator")
    private Long id;

    @Column(nullable = false)
    @Min(1)
    private int productCount;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name="product_id")
    private ProductEntity product;

    private boolean inWork;

    @Column(nullable = false)
    private String placeFrom;

    @Column(nullable = false)
    private String placeTo;

    @Column(nullable = false)
    private Date endDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "request", cascade = CascadeType.ALL)
    private ShippingEntity shipping;
}
