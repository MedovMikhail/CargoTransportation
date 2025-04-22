package com.example.cargoTransportation.controllers;

import com.example.cargoTransportation.answers.Answer;
import com.example.cargoTransportation.controllers.company.CompanyController;
import com.example.cargoTransportation.controllers.driver.DriverController;
import com.example.cargoTransportation.dto.UserDTO;
import com.example.cargoTransportation.dto.company.CompanyDTO;
import com.example.cargoTransportation.dto.driver.DriverDTO;
import com.example.cargoTransportation.services.UserService;
import com.example.cargoTransportation.services.company.CompanyService;
import com.example.cargoTransportation.services.driver.DriverService;
import com.example.cargoTransportation.utils.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private CompanyService companyService;

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id){
        UserDTO user = userService.getUser(id);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/driverOrCompany/{id}")
    public ResponseEntity<?> getDriverOrCompany(@PathVariable long id){
        UserDTO user = userService.getUser(id);
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (user.getType() == UserType.DRIVER){
            DriverDTO driver = driverService.getDriver(user.getId());
            return driver == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(driver);
        } else if (user.getType() == UserType.COMPANY) {
            CompanyDTO company = companyService.getCompany(user.getId());
            return company == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(company);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
