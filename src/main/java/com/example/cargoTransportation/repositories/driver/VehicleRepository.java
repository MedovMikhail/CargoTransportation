package com.example.cargoTransportation.repositories.driver;

import com.example.cargoTransportation.entities.driver.VehicleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    List<VehicleEntity> findAll();

    @Query(value = "select * from vehicle_entity where driver_id = :id", nativeQuery = true)
    List<VehicleEntity> findByDriverId(@Param("id") Long id);

    VehicleEntity findById(long id);

    @Transactional
    @Modifying
    @Query(value = "delete from VehicleEntity v where v.driver.userId = :id")
    void deleteByDriverId(@Param("id") long id);
}
