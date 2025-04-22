package com.example.cargoTransportation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShippingDTO {
    private Long id;
    private Long requestId;
    private Long vehicleId;
    @JsonProperty(namespace = "isComplete")
    private boolean isComplete;
    private boolean tooLate;
    private Date startDate;
    private Date endDate;
}
