package com.example.cargoTransportation.controllers.driver;

import com.example.cargoTransportation.dto.ShippingDTO;
import com.example.cargoTransportation.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getShipping(@PathVariable long id){
        ShippingDTO shipping = shippingService.getShipping(id);
        return shipping == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(shipping);
    }

    @GetMapping
    public List<ShippingDTO> getAllShipping(){
        return shippingService.getAllShipping();
    }

    @GetMapping("/search")
    public List<ShippingDTO> getShippingsByName(@RequestParam String name) {
        return shippingService.getAllShippingByName(name);
    }

    @GetMapping("/order/asc/search")
    public List<ShippingDTO> getShippingsByNameOrderByStartDateAsc(@RequestParam String name) {
        return shippingService.getAllShippingByNameOrderByStartDateAsc(name);
    }

    @GetMapping("/order/desc/search")
    public List<ShippingDTO> getShippingsByNameOrderByStartDateDesc(@RequestParam String name) {
        return shippingService.getAllShippingByNameOrderByStartDateDesc(name);
    }

    @GetMapping("/isSuccess/search")
    public List<ShippingDTO> getShippingsByNameAndIsSuccess(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsSuccess(name);
    }

    @GetMapping("/isSuccess/order/asc/search")
    public List<ShippingDTO> getShippingsByNameAndIsSuccessOrderByStartDateAsc(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsSuccessOrderByStartDateAsc(name);
    }

    @GetMapping("/isSuccess/order/desc/search")
    public List<ShippingDTO> getShippingsByNameAndIsSuccessOrderByStartDateDesc(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsSuccessOrderByStartDateDesc(name);
    }

    @GetMapping("/isFailed/search")
    public List<ShippingDTO> getShippingsByNameAndIsFailed(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsFailed(name);
    }

    @GetMapping("/isFailed/order/asc/search")
    public List<ShippingDTO> getShippingsByNameAndIsFailedOrderByStartDateAsc(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsFailedOrderByStartDateAsc(name);
    }

    @GetMapping("/isFailed/order/desc/search")
    public List<ShippingDTO> getShippingsByNameAndIsFailedOrderByStartDateDesc(@RequestParam String name) {
        return shippingService.getAllShippingByNameAndIsFailedOrderByStartDateDesc(name);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<?> getShippingByDriver(@PathVariable long driverId){
        List<ShippingDTO> list = shippingService.getByDriver(driverId);
        return list == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(list);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getShippingByCompany(@PathVariable long companyId){
        List<ShippingDTO> list = shippingService.getByCompany(companyId);
        return list == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(list);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PostMapping("/request/{requestId}/vehicle/{vehicleId}")
    public ResponseEntity<?> addShipping(@RequestBody ShippingDTO shipping, @PathVariable long requestId, @PathVariable long vehicleId){
        shipping = shippingService.addShipping(shipping, requestId, vehicleId);
        return shipping == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): new ResponseEntity<>(shipping, HttpStatus.CREATED);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateShipping(@RequestBody ShippingDTO shipping, @PathVariable long id){
        shipping = shippingService.updateShipping(shipping, id);
        return shipping == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(shipping);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShipping(@PathVariable long id){
        shippingService.deleteShipping(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @DeleteMapping("company/{companyId}")
    public ResponseEntity<?> deleteShippingByCompany(@PathVariable long companyId){
        shippingService.deleteShippingByCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
