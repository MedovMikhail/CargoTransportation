package com.example.cargoTransportation.repositories.driver;

import com.example.cargoTransportation.entities.driver.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findAll();
    List<DriverEntity> findByNameContainingIgnoreCase(String name);
    List<DriverEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    List<DriverEntity> findByNameContainingIgnoreCaseOrderByNameDesc(String name);
    List<DriverEntity> findByNameContainingIgnoreCaseOrderByDateOfBirthAsc(String name);
    List<DriverEntity> findByNameContainingIgnoreCaseOrderByDateOfBirthDesc(String name);

    @Query("select count(v) from VehicleEntity v where v.driver.userId = :id")
    int countPersonalVehicles(@Param("id") long id);

    @Query("select count(s) from ShippingEntity s where s.vehicle.driver.userId = :id and s.isComplete = true")
    int countCompletedShipmentsByDriver(@Param("id") long id);
}
