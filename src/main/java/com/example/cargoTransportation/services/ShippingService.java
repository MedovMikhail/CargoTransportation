package com.example.cargoTransportation.services;

import com.example.cargoTransportation.dto.ShippingDTO;
import com.example.cargoTransportation.entities.ShippingEntity;
import com.example.cargoTransportation.repositories.driver.ShippingRepository;
import com.example.cargoTransportation.repositories.company.CompanyRepository;
import com.example.cargoTransportation.repositories.company.RequestRepository;
import com.example.cargoTransportation.repositories.driver.DriverRepository;
import com.example.cargoTransportation.repositories.driver.VehicleRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public ShippingDTO getShipping(long id){
        checkDate();
        return mappingUtils.mapToShippingDTO(shippingRepository.findById(id).orElse(null));
    }

    public List<ShippingDTO> getByDriver(long driverId){
        checkDate();
        if (!driverRepository.existsById(driverId)) return null;
        return shippingRepository.findByVehicleDriverUserIdOrderByIsCompleteAsc(driverId).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getByCompany(long companyId){
        checkDate();
        if (!companyRepository.existsById(companyId)) return null;
        return shippingRepository.findByRequestProductCompanyUserIdOrderByIsCompleteAsc(companyId).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShipping(){
        checkDate();
        return shippingRepository.findAllByOrderByIsCompleteAsc().stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByName(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseOrderByIsCompleteAsc(name).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameOrderByStartDateAsc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseOrderByStartDateAscIsCompleteAsc(name).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameOrderByStartDateDesc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseOrderByStartDateDescIsCompleteAsc(name).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsSuccess(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLate(name, true, false).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsSuccessOrderByStartDateAsc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLateOrderByStartDateAsc(name, true, false).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsSuccessOrderByStartDateDesc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndIsCompleteAndTooLateOrderByStartDateDesc(name, true, false).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsFailed(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByIsCompleteAsc(name, true).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsFailedOrderByStartDateAsc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByStartDateAscIsCompleteAsc(name, true).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public List<ShippingDTO> getAllShippingByNameAndIsFailedOrderByStartDateDesc(String name){
        checkDate();
        return shippingRepository.findByRequestProductCompanyNameContainingIgnoreCaseAndTooLateOrderByStartDateDescIsCompleteAsc(name, true).stream()
                .map(mappingUtils::mapToShippingDTO)
                .toList();
    }

    public ShippingDTO addShipping(ShippingDTO shipping, long requestId, long vehicleId){
        checkDate();
        if (!vehicleRepository.existsById(vehicleId) || !requestRepository.existsById(requestId)) return null;

        shipping.setVehicleId(vehicleId);
        shipping.setRequestId(requestId);
        shipping.setStartDate(new Date());
        shipping.setEndDate(null);

        return mappingUtils.mapToShippingDTO(
                shippingRepository.save(
                        mappingUtils.mapToShippingEntity(shipping)
                ));
    }

    public ShippingDTO updateShipping(ShippingDTO shipping, long id){
        checkDate();
        ShippingEntity shippingEntity = shippingRepository.findById(id).orElse(null);
        if (shippingEntity == null) return null;

        shippingEntity.setComplete(shipping.isComplete());
        shippingEntity.setEndDate(new Date());

        return mappingUtils.mapToShippingDTO(shippingRepository.save(shippingEntity));
    }

    public void deleteShipping(long id){
        checkDate();
        shippingRepository.deleteById(id);
    }

    public void deleteShippingByCompany(long companyId){
        checkDate();
        List<ShippingEntity> list = getByCompany(companyId).stream()
                        .map(mappingUtils::mapToShippingEntity)
                        .toList();
        shippingRepository.deleteAllInBatch(list);
    }

    private void checkDate() {
        List<ShippingEntity> list = shippingRepository.findAll();
        list.stream()
                .filter(a -> {
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(a.getRequest().getEndDate());
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(new Date());
                    if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                        if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
                            return c2.get(Calendar.DAY_OF_MONTH) > c1.get(Calendar.DAY_OF_MONTH);
                        }
                        else return c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH);
                    } else return c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR);
                })
                .forEach(a -> {
                    a.setTooLate(true);
                    shippingRepository.save(a);
                });
    }
}
