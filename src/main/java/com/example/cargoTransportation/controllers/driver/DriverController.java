package com.example.cargoTransportation.controllers.driver;

import com.example.cargoTransportation.answers.Answer;
import com.example.cargoTransportation.dto.driver.DriverDTO;
import com.example.cargoTransportation.errors.ErrorSituation;
import com.example.cargoTransportation.securityExpression.MySecurity;
import com.example.cargoTransportation.services.driver.DriverService;
import com.example.cargoTransportation.utils.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;
    @Autowired
    private MySecurity mySecurity;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriver(@PathVariable long id){
         DriverDTO driver = driverService.getDriver(id);
         if (driver == null) {
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(driver);
    }

    @GetMapping
    public List<DriverDTO> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @GetMapping("/search")
    public List<DriverDTO> getAllDriversByName(@RequestParam String name) {
        return driverService.getAllByName(name);
    }

    @GetMapping("/order/name/asc")
    public List<DriverDTO> getAllDriversByNameOrderByNameAsc(@RequestParam String name) {
        return driverService.getAllByNameOrderByNameAsc(name);
    }

    @GetMapping("/order/name/desc")
    public List<DriverDTO> getAllDriversByNameOrderByNameDesc(@RequestParam String name) {
        return driverService.getAllByNameOrderByNameDesc(name);
    }

    @GetMapping("/order/birth/asc")
    public List<DriverDTO> getAllDriversByNameOrderByDateOfBirthAsc(@RequestParam String name) {
        return driverService.getAllByNameOrderByDateOfBirthAsc(name);
    }

    @GetMapping("/order/birth/desc")
    public List<DriverDTO> getAllDriversByNameOrderByDateOfBirthDesc(@RequestParam String name) {
        return driverService.getAllByNameOrderByDateOfBirthDesc(name);
    }

    @GetMapping("/{id}/vehicles")
    public int getCountPersonalVehicles(@PathVariable long id) {
        return driverService.getCountPersonalVehicles(id);
    }

    @GetMapping("/{id}/shippings")
    public int getCountShipmentsByDriver(@PathVariable long id) {
        return driverService.getCountShipmentsByDriver(id);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PostMapping("/{id}")
    public ResponseEntity<?> addDriver(@PathVariable long id, @RequestBody DriverDTO driver){
        Answer<DriverDTO> answer = driverService.addDriver(id, driver);
        if (answer.getBody() != null){
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } else {
            ErrorSituation error = answer.getError();
            System.out.println(error);
            return switch (error) {
                case UNDER_18, TO_OLD, NOT_MATCHING_TYPE, NOT_FOUND, SHORT_NAME, LONG_NAME ->
                        new ResponseEntity<>(answer, HttpStatus.OK);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable long id, @RequestBody DriverDTO driver){
        Answer<DriverDTO> answer = driverService.updateDriver(id, driver);
        if (answer.getBody() != null){
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } else {
            ErrorSituation error = answer.getError();
            System.out.println(error);
            return switch (error) {
                case UNDER_18, TO_OLD, ALL_READY_EXIST, NOT_MATCHING_TYPE, NOT_FOUND, SHORT_NAME, LONG_NAME ->
                        new ResponseEntity<>(answer, HttpStatus.OK);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}
