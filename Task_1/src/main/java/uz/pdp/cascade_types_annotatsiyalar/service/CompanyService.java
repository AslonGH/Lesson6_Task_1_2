package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;
import uz.pdp.cascade_types_annotatsiyalar.entity.CompanyBudget;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.Filial;
import uz.pdp.cascade_types_annotatsiyalar.payload.CompanyDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.BudgetRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.CompanyRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.FilialRepository;

import java.util.*;

@Service
public class CompanyService {


    @Autowired
    EmpCustomerRepository empCustomerRepository;

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    BudgetRepository budgetRepository;


    public ApiResponse addCompany(CompanyDto companyDto) {

         Optional<EmpCustomer> optionalEmpCustomer = empCustomerRepository.findById(companyDto.getCompanyDirectorID());
        if (!optionalEmpCustomer.isPresent())
        return new ApiResponse("Director not found", false);

          Company company = new Company();
        company.setName(companyDto.getName());
        company.setCompanyDirector(optionalEmpCustomer.get());
        companyRepository.save(company);
        return new ApiResponse("Company saved", true);

    }

    public ApiResponse editCompany(Integer uuidDto, CompanyDto companyDto) {

        Optional<Company> optionalCompany = companyRepository.findById(uuidDto);
        if (!optionalCompany.isPresent())
        return new ApiResponse("Company not found", false);

        Optional<EmpCustomer> optionalEmpCustomer = empCustomerRepository.findById(companyDto.getCompanyDirectorID());
        if (!optionalEmpCustomer.isPresent())
        return new ApiResponse("Director not found", false);

          Company company = optionalCompany.get();
        company.setName(companyDto.getName());
        company.setCompanyDirector(optionalEmpCustomer.get());
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("company not found", false);
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("company deleted", true);
        } catch (Exception e) {
            return new ApiResponse("company not deleted", false);
        }
    }
}




