package com.example.cargoTransportation.controllers.company;

import com.example.cargoTransportation.dto.company.ProductDTO;
import com.example.cargoTransportation.services.company.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id){
        ProductDTO product = productService.getProduct(id);
        return product == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(product);
    }

    @GetMapping
    public List<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getProductsByCompany(@PathVariable long companyId){
        List<ProductDTO> list = productService.getProductsByCompany(companyId);
        return list == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public List<ProductDTO> getProductsByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping("/order/name/asc")
    public List<ProductDTO> getProductsByNameOrderByNameAsc(@RequestParam String search) {
        return productService.getProductsByNameOrderByNameAsc(search);
    }

    @GetMapping("/order/name/desc")
    public List<ProductDTO> getProductsByNameOrderByNameDesc(@RequestParam String search) {
        return productService.getProductsByNameOrderByNameDesc(search);
    }

    @GetMapping("/order/weight/asc")
    public List<ProductDTO> getProductsByNameOrderByWeightAsc(@RequestParam String search) {
        return productService.getProductsByNameOrderByWeightAsc(search);
    }

    @GetMapping("/order/weight/desc")
    public List<ProductDTO> getProductsByNameOrderByWeightDesc(@RequestParam String search) {
        return productService.getProductsByNameOrderByWeightDesc(search);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @PostMapping("/company/{companyId}")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product, @PathVariable long companyId){
        product = productService.addProduct(product, companyId);
        return product == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST): new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestParam int weight, @PathVariable long id){
        ProductDTO product = productService.updateProduct(weight, id);
        return product == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(product);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@mySecurity.isCompany(authentication.principal.type)")
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<?> deleteProductsByCompany(@PathVariable long companyId){
        productService.deleteProductByCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
