package com.example.cargoTransportation.services.company;

import com.example.cargoTransportation.dto.company.RequestDTO;
import com.example.cargoTransportation.entities.company.RequestEntity;
import com.example.cargoTransportation.repositories.company.ProductRepository;
import com.example.cargoTransportation.repositories.company.RequestRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public RequestDTO getRequest(long id){
        return mappingUtils.mapToRequestDTO(requestRepository.findById(id).orElse(null));
    }

    public List<RequestDTO> getAllRequests(){
        return requestRepository.findAllByOrderByInWorkAsc().stream()
                .map(mappingUtils::mapToRequestDTO)
                .toList();
    }

    public List<RequestDTO> getAllByCompany(long companyId){
        return requestRepository.findByProductCompanyUserIdOrderByInWorkAsc(companyId).stream()
                .map(mappingUtils::mapToRequestDTO)
                .toList();
    }

    public List<RequestDTO> getAllByCompanyNameAndProductName(String companyName, String productName){
        return requestRepository.findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCase(companyName, productName).stream()
                .map(mappingUtils::mapToRequestDTO)
                .toList();
    }

    public List<RequestDTO> getAllByCompanyNameAndProductNameOrderByEndDateAsc(String companyName, String productName){
        return requestRepository.findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCaseOrderByEndDateAsc(companyName, productName).stream()
                .map(mappingUtils::mapToRequestDTO)
                .toList();
    }

    public List<RequestDTO> getAllByCompanyNameAndProductNameOrderByEndDateDesc(String companyName, String productName){
        return requestRepository.findByProductCompanyNameContainingIgnoreCaseAndProductNameContainingIgnoreCaseOrderByEndDateDesc(companyName, productName).stream()
                .map(mappingUtils::mapToRequestDTO)
                .toList();
    }

    public RequestDTO addRequest(RequestDTO request, long productId){
        if (!productRepository.existsById(productId) || request == null) return null;

        request.setProductId(productId);
        return mappingUtils.mapToRequestDTO(
                requestRepository.save(
                        mappingUtils.mapToRequestEntity(request)
                ));
    }

    public RequestDTO updateRequest(RequestDTO request, long id){
        RequestEntity requestEntity = requestRepository.findById(id).orElse(null);
        if (requestEntity == null) return null;

        requestEntity.setEndDate(request.getEndDate());
        requestEntity.setProductCount(request.getProductCount());
        requestEntity.setInWork(request.isInWork());

        return mappingUtils.mapToRequestDTO(requestRepository.save(requestEntity));
    }

    public void deleteRequest(long id){
        requestRepository.deleteById(id);
    }

    public void deleteByCompany(long companyId){
        List<RequestEntity> list = getAllByCompany(companyId).stream()
                .map(mappingUtils::mapToRequestEntity)
                .toList();
        requestRepository.deleteAllInBatch(list);
    }
}
