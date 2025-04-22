package com.example.cargoTransportation.repositories.company;

import com.example.cargoTransportation.entities.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    List<CompanyEntity> findByNameContainingIgnoreCase(String name);
    List<CompanyEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    List<CompanyEntity> findByNameContainingIgnoreCaseOrderByNameDesc(String name);

    @Query("select count(p) from ProductEntity p where p.company.userId = :id")
    int countProductsByCompany(@Param("id") long id);

    @Query("select count(r) from RequestEntity r where r.product.company.userId = :id")
    int countRequestsByCompany(@Param("id") long id);

    @Query("select count(s) from ShippingEntity s where s.request.product.company.userId = :id and s.isComplete = true")
    int countShipmentsByCompany(@Param("id") long id);
}
