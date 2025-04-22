package com.example.cargoTransportation.repositories.company;

import com.example.cargoTransportation.entities.company.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findByProductCompanyUserIdOrderByInWorkAsc(long companyId);
    List<RequestEntity> findAllByOrderByInWorkAsc();

    List<RequestEntity> findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCase(String companyName, String productName);
    List<RequestEntity> findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCaseOrderByEndDateAsc(String companyName, String productName);
    List<RequestEntity> findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCaseOrderByEndDateDesc(String companyName, String productName);
}
