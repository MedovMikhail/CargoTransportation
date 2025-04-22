package com.example.cargoTransportation.repositories.company;

import com.example.cargoTransportation.entities.company.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCompanyUserId(long companyId);
    List<ProductEntity> findByNameAndWeight(String name, double weight);
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseOrderByNameDesc(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseOrderByWeightAsc(String name);
    List<ProductEntity> findByNameContainingIgnoreCaseOrderByWeightDesc(String name);
}
