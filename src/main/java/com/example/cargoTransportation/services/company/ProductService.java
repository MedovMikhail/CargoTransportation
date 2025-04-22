package com.example.cargoTransportation.services.company;

import com.example.cargoTransportation.dto.company.ProductDTO;
import com.example.cargoTransportation.entities.company.ProductEntity;
import com.example.cargoTransportation.repositories.company.CompanyRepository;
import com.example.cargoTransportation.repositories.company.ProductRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public ProductDTO getProduct(long id){
        return mappingUtils.mapToProductDTO(productRepository.findById(id).orElse(null));
    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByCompany(long companyId){
        if (!companyRepository.existsById(companyId)) return null;
        return productRepository.findByCompanyUserId(companyId).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByNameOrderByNameAsc(String name) {
        return productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByNameOrderByNameDesc(String name) {
        return productRepository.findByNameContainingIgnoreCaseOrderByNameDesc(name).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByNameOrderByWeightAsc(String name) {
        return productRepository.findByNameContainingIgnoreCaseOrderByWeightAsc(name).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByNameOrderByWeightDesc(String name) {
        return productRepository.findByNameContainingIgnoreCaseOrderByWeightDesc(name).stream()
                .map(mappingUtils::mapToProductDTO)
                .toList();
    }

    public ProductDTO addProduct(ProductDTO product, long companyId){
        if (!companyRepository.existsById(companyId) || product == null) return null;
        if (productRepository.findByNameAndWeight(product.getName(), product.getWeight()).stream()
                .anyMatch(productEntity -> productEntity.getCompany().getUserId() == companyId)) return null;

        product.setCompanyId(companyId);
        return mappingUtils.mapToProductDTO(
                productRepository.save(
                        mappingUtils.mapToProductEntity(product)
                ));
    }

    public ProductDTO updateProduct(int weight, long id){
        ProductDTO product = mappingUtils.mapToProductDTO(productRepository.findById(id).orElse(null));
        if (product == null) return null;

        product.setWeight(weight);
        return mappingUtils.mapToProductDTO(
                productRepository.save(
                        mappingUtils.mapToProductEntity(product)
                ));
    }

    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    public void deleteProductByCompany(long companyId){
        List<ProductEntity> products = productRepository.findByCompanyUserId(companyId);
        productRepository.deleteAllInBatch(products);
    }
}
