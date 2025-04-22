package com.example.cargoTransportation.dto.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private double weight;
    private Long companyId;
}
