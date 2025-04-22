package com.example.cargoTransportation.securityExpression;

import com.example.cargoTransportation.utils.UserType;
import org.springframework.stereotype.Component;

@Component
public class MySecurity {

    public boolean isDriver(UserType type) {
        return type == UserType.DRIVER;
    }

    public boolean isCompany(UserType type) {
        return type == UserType.COMPANY;
    }

}
