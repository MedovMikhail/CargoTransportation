package com.example.cargoTransportation.utils;

import com.example.cargoTransportation.dto.*;
import com.example.cargoTransportation.dto.company.CompanyDTO;
import com.example.cargoTransportation.dto.company.ProductDTO;
import com.example.cargoTransportation.dto.company.RequestDTO;
import com.example.cargoTransportation.dto.driver.DriverDTO;
import com.example.cargoTransportation.dto.driver.VehicleDTO;
import com.example.cargoTransportation.entities.ShippingEntity;
import com.example.cargoTransportation.entities.company.CompanyEntity;
import com.example.cargoTransportation.entities.company.ProductEntity;
import com.example.cargoTransportation.entities.company.RequestEntity;
import com.example.cargoTransportation.entities.driver.DriverEntity;
import com.example.cargoTransportation.entities.UserEntity;
import com.example.cargoTransportation.entities.driver.VehicleEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public UserDTO mapToUserDTO(UserEntity from){
        if (from == null) return null;

        UserDTO to = new UserDTO();

        to.setId(from.getId());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
        to.setType(from.getType());

        return to;
    }

    public UserEntity mapToUserEntity(UserDTO from){
        if (from == null) return null;

        UserEntity to = new UserEntity();

        to.setId(from.getId());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
        to.setType(from.getType());

        return to;
    }

    public DriverDTO mapToDriverDTO(DriverEntity from){
        if (from == null) return null;

        DriverDTO to = new DriverDTO();

        to.setUserId(from.getUserId());
        to.setName(from.getName());
        to.setDateOfBirth(from.getDateOfBirth());
        to.setDrive(from.isDrive());

        return to;
    }

    public DriverEntity mapToDriverEntity(DriverDTO from){
        if (from == null) return null;

        DriverEntity to = new DriverEntity();

        to.setUserId(from.getUserId());
        to.setName(from.getName());
        to.setDateOfBirth(from.getDateOfBirth());
        to.setDrive(from.isDrive());

        return to;
    }

    public VehicleDTO mapToVehicleDTO(VehicleEntity from){
        if (from == null) return null;

        VehicleDTO to = new VehicleDTO();

        to.setId(from.getId());
        to.setDriverId(from.getDriver().getUserId());
        to.setName(from.getName());
        to.setLiftWeight(from.getLiftWeight());

        return to;
    }

    public VehicleEntity mapToVehicleEntity(VehicleDTO from){
        if (from == null) return null;

        VehicleEntity to = new VehicleEntity();

        to.setId(from.getId());
        to.setDriver(new DriverEntity());
        to.getDriver().setUserId(from.getDriverId());
        to.setName(from.getName());
        to.setLiftWeight(from.getLiftWeight());

        return to;
    }

    public CompanyDTO mapToCompanyDTO(CompanyEntity from){
        if (from == null) return null;

        CompanyDTO to = new CompanyDTO();

        to.setUserId(from.getUserId());
        to.setName(from.getName());
        to.setIndustry(from.getIndustry());
        to.setDescription(from.getDescription());

        return to;
    }

    public CompanyEntity mapToCompanyEntity(CompanyDTO from){
        if (from == null) return null;

        CompanyEntity to = new CompanyEntity();

        to.setUserId(from.getUserId());
        to.setName(from.getName());
        to.setIndustry(from.getIndustry());
        to.setDescription(from.getDescription());

        return to;
    }

    public ProductDTO mapToProductDTO(ProductEntity from){
        if (from == null) return null;

        ProductDTO to = new ProductDTO();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setWeight(from.getWeight());
        to.setCompanyId(from.getCompany().getUserId());

        return to;
    }

    public ProductEntity mapToProductEntity(ProductDTO from){
        if (from == null) return null;

        ProductEntity to = new ProductEntity();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setWeight(from.getWeight());
        to.setCompany(new CompanyEntity());
        to.getCompany().setUserId(from.getCompanyId());

        return to;
    }

    public RequestDTO mapToRequestDTO(RequestEntity from){
        if (from == null) return null;

        RequestDTO to = new RequestDTO();

        to.setId(from.getId());
        to.setPlaceFrom(from.getPlaceFrom());
        to.setInWork(from.isInWork());
        to.setPlaceTo(from.getPlaceTo());
        to.setEndDate(from.getEndDate());
        to.setProductCount(from.getProductCount());
        to.setProductId(from.getProduct().getId());

        return to;
    }

    public RequestEntity mapToRequestEntity(RequestDTO from){
        if (from == null) return null;

        RequestEntity to = new RequestEntity();

        to.setId(from.getId());
        to.setInWork(from.isInWork());
        to.setPlaceFrom(from.getPlaceFrom());
        to.setPlaceTo(from.getPlaceTo());
        to.setEndDate(from.getEndDate());
        to.setProductCount(from.getProductCount());
        to.setProduct(new ProductEntity());
        to.getProduct().setId(from.getProductId());

        return to;
    }

    public ShippingDTO mapToShippingDTO(ShippingEntity from){
        if (from == null) return null;

        ShippingDTO to = new ShippingDTO();

        to.setId(from.getId());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setComplete(from.isComplete());
        to.setTooLate(from.isTooLate());
        to.setRequestId(from.getRequest().getId());
        to.setVehicleId(from.getVehicle().getId());

        return to;
    }

    public ShippingEntity mapToShippingEntity(ShippingDTO from){
        if (from == null) return null;

        ShippingEntity to = new ShippingEntity();

        to.setId(from.getId());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setComplete(from.isComplete());
        to.setTooLate(from.isTooLate());
        to.setRequest(new RequestEntity());
        to.getRequest().setId(from.getRequestId());
        to.setVehicle(new VehicleEntity());
        to.getVehicle().setId(from.getVehicleId());

        return to;
    }
}
