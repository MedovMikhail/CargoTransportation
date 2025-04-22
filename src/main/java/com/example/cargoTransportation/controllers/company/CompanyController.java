package com.example.cargoTransportation.controllers.company;

import com.example.cargoTransportation.answers.Answer;
import com.example.cargoTransportation.dto.company.CompanyDTO;
import com.example.cargoTransportation.dto.driver.DriverDTO;
import com.example.cargoTransportation.errors.ErrorSituation;
import com.example.cargoTransportation.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable long id){
        CompanyDTO company = companyService.getCompany(id);
        return company == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(company);
    }

    @GetMapping
    public List<CompanyDTO> getCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/search")
    public List<CompanyDTO> getCompaniesByName(@RequestParam String name){
        return companyService.getAllByName(name);
    }

    @GetMapping("/order/asc")
    public List<CompanyDTO> getCompaniesByNameOrderAsc(@RequestParam String name){
        return companyService.getAllByNameAsc(name);
    }

    @GetMapping("/order/desc")
    public List<CompanyDTO> getCompaniesByNameOrderDesc(@RequestParam String name){
        return companyService.getAllByNameDesc(name);
    }

    @GetMapping("/{id}/products")
    public int getCountProductsByDriver(@PathVariable long id) {
        return companyService.getCountProductsByCompany(id);
    }

    @GetMapping("/{id}/requests")
    public int getCountRequestsByDriver(@PathVariable long id) {
        return companyService.getCountRequestsByCompany(id);
    }

    @GetMapping("/{id}/shippings")
    public int getCountShipmentsByDriver(@PathVariable long id) {
        return companyService.getCountShipmentsByCompany(id);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @PostMapping("/{userId}")
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO company, @PathVariable long userId){
        Answer<CompanyDTO> answer = companyService.addCompany(company, userId);
        if (answer.getBody() != null){
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } else {
            ErrorSituation error = answer.getError();
            return switch (error) {
                case NOT_MATCHING_TYPE, NOT_FOUND, SHORT_NAME, LONG_NAME ->
                        new ResponseEntity<>(answer, HttpStatus.OK);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@RequestBody CompanyDTO company, @PathVariable long id){
        Answer answer = companyService.updateCompany(company, id);
        if (answer.getBody() != null){
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } else {
            ErrorSituation error = answer.getError();
            return switch (error) {
                case NOT_MATCHING_TYPE, NOT_FOUND, SHORT_NAME, LONG_NAME ->
                        new ResponseEntity<>(answer, HttpStatus.OK);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            };
        }
    }
}
