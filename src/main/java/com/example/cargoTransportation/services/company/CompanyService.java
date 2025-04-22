package com.example.cargoTransportation.services.company;

import com.example.cargoTransportation.answers.Answer;
import com.example.cargoTransportation.dto.company.CompanyDTO;
import com.example.cargoTransportation.entities.UserEntity;
import com.example.cargoTransportation.entities.company.CompanyEntity;
import com.example.cargoTransportation.errors.ErrorSituation;
import com.example.cargoTransportation.repositories.UserRepository;
import com.example.cargoTransportation.repositories.company.CompanyRepository;
import com.example.cargoTransportation.utils.MappingUtils;
import com.example.cargoTransportation.utils.UserType;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public CompanyDTO getCompany(long id){
        return mappingUtils.mapToCompanyDTO(companyRepository.findById(id).orElse(null));
    }

    public List<CompanyDTO> getAllCompanies(){
        return companyRepository.findAll().stream()
                .map(mappingUtils::mapToCompanyDTO)
                .toList();
    }

    public List<CompanyDTO> getAllByName(String name){
        return companyRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mappingUtils::mapToCompanyDTO)
                .toList();
    }

    public List<CompanyDTO> getAllByNameAsc(String name){
        return companyRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(mappingUtils::mapToCompanyDTO)
                .toList();
    }

    public List<CompanyDTO> getAllByNameDesc(String name){
        return companyRepository.findByNameContainingIgnoreCaseOrderByNameDesc(name).stream()
                .map(mappingUtils::mapToCompanyDTO)
                .toList();
    }

    public int getCountProductsByCompany(long id) {return companyRepository.countProductsByCompany(id);}

    public int getCountRequestsByCompany(long id) {return companyRepository.countRequestsByCompany(id);}

    public int getCountShipmentsByCompany(long id) {return companyRepository.countShipmentsByCompany(id);}

    public Answer<CompanyDTO> addCompany(CompanyDTO company, long id){
        UserEntity user = userRepository.findById(id);
        if (user == null) return Answer.answer(ErrorSituation.NOT_FOUND);
        if (user.getType() != UserType.COMPANY) return Answer.answer(ErrorSituation.NOT_MATCHING_TYPE);
        if (companyRepository.existsById(id)) return Answer.answer(ErrorSituation.ALL_READY_EXIST);

        company.setUserId(id);
        CompanyEntity companyEntity = mappingUtils.mapToCompanyEntity(company);
        try {
            return Answer.answer(mappingUtils.mapToCompanyDTO(companyRepository.save(companyEntity)));
        } catch (PersistenceException e) {
            System.out.println(e);
            return Answer.answer(ErrorSituation.ERROR);
        }
    }

    public Answer<CompanyDTO> updateCompany(CompanyDTO company, long id){
        CompanyEntity companyEntity = companyRepository.findById(id).orElse(null);
        if (company == null) return Answer.answer(ErrorSituation.NOT_FOUND);
        try {
            companyEntity.setName(company.getName());
            companyEntity.setIndustry(company.getIndustry());
            companyEntity.setDescription(company.getDescription());
            companyEntity = companyRepository.save(companyEntity);
            return Answer.answer(mappingUtils.mapToCompanyDTO(companyEntity));
        } catch (NonUniqueResultException e){
            return Answer.answer(ErrorSituation.ALL_READY_EXIST);
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Answer.answer(ErrorSituation.ERROR);
        }
    }
}
