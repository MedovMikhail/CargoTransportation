package com.example.cargoTransportation.dto;

import com.example.cargoTransportation.utils.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private UserType type;
}
