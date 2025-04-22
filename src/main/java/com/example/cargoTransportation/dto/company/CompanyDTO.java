package com.example.cargoTransportation.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long userId;
    private String name;
    private String industry;
    private String description;

    public CompanyDTO(String name){
        this.name = name;
    }
}
