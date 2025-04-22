package com.example.cargoTransportation.services.driver;

import com.example.cargoTransportation.dto.driver.VehicleDTO;
import com.example.cargoTransportation.entities.driver.VehicleEntity;
import com.example.cargoTransportation.repositories.driver.DriverRepository;
import com.example.cargoTransportation.repositories.driver.VehicleRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public List<VehicleDTO> getAllVehicles(){
        return vehicleRepository.findAll().stream()
                .map(mappingUtils::mapToVehicleDTO)
                .toList();
    }

    public List<VehicleDTO> getAllVehicleOfDriver(long id){
        if (!driverRepository.existsById(id)) return null;
        return vehicleRepository.findByDriverId(id).stream()
                .map(mappingUtils::mapToVehicleDTO)
                .toList();
    }

    public VehicleDTO getVehicle(long id){
        VehicleEntity vehicle = vehicleRepository.findById(id);
        if (vehicle == null) return null;
        return mappingUtils.mapToVehicleDTO(vehicle);
    }

    public VehicleDTO addVehicle(long id, VehicleDTO vehicle){
        if (!driverRepository.existsById(id)) return null;
        vehicle.setDriverId(id);
        try {
            return mappingUtils.mapToVehicleDTO(
                    vehicleRepository.save(
                            mappingUtils.mapToVehicleEntity(vehicle)
                    ));
        } catch (Exception e){
            return null;
        }
    }

    public VehicleDTO updateVehicle(long id, int liftWeight){
        VehicleEntity vehicle = mappingUtils.mapToVehicleEntity(getVehicle(id));
        if (vehicle == null) return null;
        vehicle.setLiftWeight(liftWeight);
        vehicle = vehicleRepository.save(vehicle);
        return mappingUtils.mapToVehicleDTO(vehicle);
    }

    public void deleteVehicle(long id){
        vehicleRepository.deleteById(id);
    }

    public void deleteVehiclesOfDriver(long id){
        vehicleRepository.deleteByDriverId(id);
    }
}
