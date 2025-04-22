package com.example.cargoTransportation.dto.company;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestDTO {
    private Long id;
    private int productCount;
    private boolean inWork;
    private String placeFrom;
    private String placeTo;
    private Date endDate;
    private Long productId;
}
