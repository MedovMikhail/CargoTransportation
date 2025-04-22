package com.example.cargoTransportation.dto.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private Long userId;
    private String name;
    private Date dateOfBirth;
    @JsonProperty(namespace = "isDrive")
    private boolean isDrive;

    public DriverDTO(String name, Date dateOfBirth){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        isDrive = false;
    }
}
