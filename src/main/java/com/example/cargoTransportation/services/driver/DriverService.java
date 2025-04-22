package com.example.cargoTransportation.services.driver;

import com.example.cargoTransportation.answers.Answer;
import com.example.cargoTransportation.dto.driver.DriverDTO;
import com.example.cargoTransportation.entities.UserEntity;
import com.example.cargoTransportation.entities.driver.DriverEntity;
import com.example.cargoTransportation.errors.ErrorSituation;
import com.example.cargoTransportation.repositories.driver.DriverRepository;
import com.example.cargoTransportation.repositories.UserRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import com.example.cargoTransportation.utils.UserType;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.List;

@Slf4j
@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public List<DriverDTO> getAllDrivers(){
        return driverRepository.findAll().stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public DriverDTO getDriver(long id){
        return mappingUtils.mapToDriverDTO(driverRepository.findById(id).orElse(null));
    }

    public List<DriverDTO> getAllByName(String name) {
        return driverRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public List<DriverDTO> getAllByNameOrderByNameAsc(String name) {
        return driverRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public List<DriverDTO> getAllByNameOrderByNameDesc(String name) {
        return driverRepository.findByNameContainingIgnoreCaseOrderByNameDesc(name).stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public List<DriverDTO> getAllByNameOrderByDateOfBirthAsc(String name) {
        return driverRepository.findByNameContainingIgnoreCaseOrderByDateOfBirthAsc(name).stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public List<DriverDTO> getAllByNameOrderByDateOfBirthDesc(String name) {
        return driverRepository.findByNameContainingIgnoreCaseOrderByDateOfBirthDesc(name).stream()
                .map(mappingUtils::mapToDriverDTO)
                .toList();
    }

    public int getCountPersonalVehicles(long id) {
        return driverRepository.countPersonalVehicles(id);
    }

    public int getCountShipmentsByDriver(long id) {
        return driverRepository.countCompletedShipmentsByDriver(id);
    }

    public Answer<DriverDTO> addDriver(long id, DriverDTO driver){
        UserEntity user = userRepository.findById(id);
        if (user == null) return Answer.answer(ErrorSituation.NOT_FOUND);
        if (user.getType() != UserType.DRIVER) return Answer.answer(ErrorSituation.NOT_MATCHING_TYPE);
        if (driver.getName().length() < 4) return Answer.answer(ErrorSituation.SHORT_NAME);
        if (driver.getName().length() > 20) return Answer.answer(ErrorSituation.LONG_NAME);
        DriverEntity driverEntity = mappingUtils.mapToDriverEntity(driver);
        driverEntity.setUserId(id);
        try {
            driver = mappingUtils.mapToDriverDTO(driverRepository.save(driverEntity));
            return Answer.answer(driver);
        } catch (NonUniqueResultException e){
            return Answer.answer(ErrorSituation.ALL_READY_EXIST);
        } catch (PersistenceException e){
            return Answer.answer(ErrorSituation.ERROR);
        } catch (ConstraintViolationException e) {
            return Answer.answer(ErrorSituation.UNDER_18);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Answer.answer(ErrorSituation.UNDER_18);
        }
    }

    public Answer<DriverDTO> updateDriver(long id, DriverDTO driverDTO){
        DriverEntity driver = driverRepository.findById(id).orElse(null);
        if (driver == null) return Answer.answer(ErrorSituation.NOT_FOUND);
        if (driverDTO.getDateOfBirth() != null) driver.setDateOfBirth(driverDTO.getDateOfBirth());
        if (driverDTO.getName().length() < 4) return Answer.answer(ErrorSituation.SHORT_NAME);
        if (driverDTO.getName().length() > 20) return Answer.answer(ErrorSituation.LONG_NAME);
        try {
            driver = driverRepository.save(mappingUtils.mapToDriverEntity(driverDTO));
            return Answer.answer(mappingUtils.mapToDriverDTO(driver));
        } catch (DataIntegrityViolationException e) {
            return Answer.answer(ErrorSituation.ALL_READY_EXIST);
        } catch (Exception e) {
            System.out.println(e);
            return Answer.answer(ErrorSituation.ERROR);
        }
    }
}
