package com.example.cargoTransportation.controllers;

import com.example.cargoTransportation.dto.UserDTO;
import com.example.cargoTransportation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserDTO user){
        HashMap<String, UserDTO> response = userService.register(user);
        if (response == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = response.keySet().stream().findFirst().get();
        return ResponseEntity.ok(String.format("\"%s\"",token));
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserDTO user){
        String token = userService.login(user);
        return String.format("\"%s\"",token);
    }
}
