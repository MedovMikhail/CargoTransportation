package com.example.cargoTransportation.controllers.driver;

import com.example.cargoTransportation.dto.driver.VehicleDTO;
import com.example.cargoTransportation.services.driver.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<VehicleDTO> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<?> getAllDriverVehicles(@PathVariable long id){
        List<VehicleDTO> vehicles = vehicleService.getAllVehicleOfDriver(id);
        if (vehicles == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicle(@PathVariable long id){
        VehicleDTO vehicle = vehicleService.getVehicle(id);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PostMapping("/driver/{id}")
    public ResponseEntity<?> addVehicle(@PathVariable long id, @RequestBody VehicleDTO vehicle){
        vehicle = vehicleService.addVehicle(id, vehicle);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable long id, @RequestParam int liftWeight){
        VehicleDTO vehicle = vehicleService.updateVehicle(id, liftWeight);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable long id){
        vehicleService.deleteVehicle(id);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @DeleteMapping("/driver/{id}")
    public void deleteVehiclesOfDriver(@PathVariable long id){
        vehicleService.deleteVehiclesOfDriver(id);
    }
}
