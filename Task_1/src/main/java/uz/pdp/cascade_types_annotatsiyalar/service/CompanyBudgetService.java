package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.payload.CompanyBudgetDto;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;
import uz.pdp.cascade_types_annotatsiyalar.entity.CompanyBudget;
import uz.pdp.cascade_types_annotatsiyalar.repository.BudgetRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.CompanyRepository;

import java.util.*;

@Service
public class CompanyBudgetService {


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    BudgetRepository  budgetRepository;


    public ApiResponse addCompanyBudget(CompanyBudgetDto budgetDto) {

          CompanyBudget companyBudget = new CompanyBudget();
        Optional<Company> optionalCompany = companyRepository.findByName("UCELL");
        if (!optionalCompany.isPresent()) {
         return new ApiResponse("Budget not found", false);
        }
        companyBudget.setCompany(optionalCompany.get());
        companyBudget.setIncomeType(budgetDto.getIncomeType());
        companyBudget.setLocalDate(budgetDto.getLocalDate());

       if (companyBudget.getBalance()!=null) {
           companyBudget.setBalance(companyBudget.getBalance()+budgetDto.getIncome());
       }else {
           companyBudget.setBalance(budgetDto.getIncome());
       }
       budgetRepository.save(companyBudget);
       return new ApiResponse("Budget saved", true);
    }

    public ApiResponse editCompany(UUID uuidDto, CompanyBudgetDto companyDto) {

        Optional<CompanyBudget> optionalBudget = budgetRepository.findById(uuidDto);
        if (!optionalBudget.isPresent())
            return new ApiResponse("Budget not found", false);

        Optional<Company> optionalCompany = companyRepository.findByName("UCELL");
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Budget not found", false);
        }

         CompanyBudget companyBudget = optionalBudget.get();
        companyBudget.setIncomeType(companyDto.getIncomeType());
        companyBudget.setLocalDate(companyDto.getLocalDate());
        companyBudget.setBalance(companyDto.getIncome());
        companyBudget.setCompany(optionalCompany.get());
        budgetRepository.save(companyBudget);
        return new ApiResponse("Budget edited", true);
    }

    public ApiResponse deleteCompanyBudget(UUID id) {
        Optional<CompanyBudget> byId = budgetRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("budget not found", false);
        try {
            budgetRepository.deleteById(id);
            return new ApiResponse("budget deleted", true);
        } catch (Exception e) {
            return new ApiResponse("budget not deleted", false);
        }
    }

    public ApiResponse getBudget() {
        return new ApiResponse(budgetRepository.findAll(),true);
    }

}







