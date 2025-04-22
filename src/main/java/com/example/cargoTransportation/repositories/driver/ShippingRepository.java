package com.example.cargoTransportation.repositories.driver;

import com.example.cargoTransportation.entities.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {
    List<ShippingEntity> findByVehicleDriverUserIdOrderByIsCompleteAsc(long driverId);
    List<ShippingEntity> findByRequestProductCompanyUserIdOrderByIsCompleteAsc(long companyId);

    List<ShippingEntity> findAllByOrderByIsCompleteAsc();

    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseOrderByIsCompleteAsc(String name);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseOrderByStartDateAscIsCompleteAsc(String name);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseOrderByStartDateDescIsCompleteAsc(String name);

    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLate(String name, boolean isComplete, boolean tooLate);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLateOrderByStartDateAsc(String name, boolean isComplete, boolean tooLate);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLateOrderByStartDateDesc(String name, boolean isComplete, boolean tooLate);

    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByIsCompleteAsc(String name, boolean tooLate);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByStartDateAscIsCompleteAsc(String name, boolean tooLate);
    List<ShippingEntity> findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByStartDateDescIsCompleteAsc(String name, boolean tooLate);
}
