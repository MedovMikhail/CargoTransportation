package com.example.cargoTransportation.controllers.company;

import com.example.cargoTransportation.dto.company.RequestDTO;
import com.example.cargoTransportation.services.company.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getRequest(@PathVariable long id){
        RequestDTO request = requestService.getRequest(id);
        return request == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(request);
    }

    @GetMapping
    public List<RequestDTO> getRequests(){
        return requestService.getAllRequests();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getRequestsByCompany(@PathVariable long companyId){
        List<RequestDTO> list = requestService.getAllByCompany(companyId);
        return list == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(list);
    }

    @GetMapping("/company/product/search")
    public List<RequestDTO> getRequestsByCompanyNameAndProductName(@RequestParam String companyName, @RequestParam String productName){
        return requestService.getAllByCompanyNameAndProductName(companyName, productName);
    }

    @GetMapping("/company/product/order/asc/search")
    public List<RequestDTO> getRequestsByCompanyNameAndProductNameOrderByEndDateAsc(@RequestParam String companyName, @RequestParam String productName){
        return requestService.getAllByCompanyNameAndProductNameOrderByEndDateAsc(companyName, productName);
    }

    @GetMapping("/company/product/order/desc/search")
    public List<RequestDTO> getRequestsByCompanyNameAndProductNameOrderByEndDateDesc(@RequestParam String companyName, @RequestParam String productName){
        return requestService.getAllByCompanyNameAndProductNameOrderByEndDateDesc(companyName, productName);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @PostMapping("/product/{productId}")
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO request, @PathVariable long productId){
        request = requestService.addRequest(request, productId);
        return request == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PreAuthorize("@mySecurity.isDriver(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@RequestBody RequestDTO request, @PathVariable long id){
        request = requestService.updateRequest(request, id);
        return request == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable long id){
        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<?> deleteRequestsByCompany(@PathVariable long companyId){
        requestService.deleteByCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
