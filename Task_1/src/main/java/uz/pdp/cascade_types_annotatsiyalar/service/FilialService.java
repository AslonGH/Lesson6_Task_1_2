package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.Filial;
import uz.pdp.cascade_types_annotatsiyalar.payload.FilialDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.CompanyRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.FilialRepository;

import java.util.*;

@Service
public class FilialService {


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmpCustomerRepository empCustomerRepository;

    @Autowired
    FilialRepository filialRepository;


    public ApiResponse addFilial(FilialDto filialDto) {


         Optional<EmpCustomer> optionalManager = empCustomerRepository.findById(filialDto.getFilialManagerId());
        if (!optionalManager.isPresent())
        return new ApiResponse("Manager not found", false);

        Optional<Company> optionalCompany = companyRepository.findById(filialDto.getCompanyId());
        if (!optionalCompany.isPresent())
        return new ApiResponse("Company not found", false);

        Optional<EmpCustomer> optionalDirector = empCustomerRepository.findById(filialDto.getFilialManagerId());
        if (!optionalDirector.isPresent())
            return new ApiResponse("Director not found", false);

        Set<EmpCustomer> filialEmployees = new HashSet<>();
        List<UUID> filialEmployeeId = filialDto.getFilialEmployeeIds();
        for (UUID uuid : filialEmployeeId) {
            Optional<EmpCustomer> optionalEmployee = empCustomerRepository.findById(uuid);
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Employee not found", false);
            filialEmployees.add(optionalEmployee.get());
        }

         Filial filial = new Filial();
        filial.setName(filialDto.getName());
        filial.setCity(filialDto.getCity());
        filial.setFilialEmployees(filialEmployees);
        filial.setFilialManager(optionalManager.get());
        filial.setFilialDirector(optionalDirector.get());
        filial.setCompany(optionalCompany.get());
        filialRepository.save(filial);
        return new ApiResponse("Filial saved", true);

    }

    public ApiResponse editFilial(UUID uuidDto, FilialDto filialDto) {

        Optional<Filial> byId = filialRepository.findById(uuidDto);
        if (!byId.isPresent())
            return new ApiResponse("Filial not found", false);


        Optional<EmpCustomer> optionalManager = empCustomerRepository.findById(filialDto.getFilialManagerId());
        if (!optionalManager.isPresent())
            return new ApiResponse("Manager not found", false);

        Optional<EmpCustomer> optionalDirector = empCustomerRepository.findById(filialDto.getFilialManagerId());
        if (!optionalDirector.isPresent())
            return new ApiResponse("Director not found", false);

        Optional<Company> optionalCompany = companyRepository.findById(filialDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found", false);


         Set<EmpCustomer> filialEmployees = new HashSet<>();
        List<UUID> filialEmployeeId = filialDto.getFilialEmployeeIds();
        for (UUID uuid : filialEmployeeId) {
            Optional<EmpCustomer> optionalEmployee = empCustomerRepository.findById(uuid);
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Employee not found", false);
            filialEmployees.add(optionalEmployee.get());
        }
         Filial filial = byId.get();
        filial.setName(filialDto.getName());
        filial.setCity(filialDto.getCity());
        filial.setFilialEmployees(filialEmployees);
        filial.setFilialManager(optionalManager.get());
        filial.setFilialDirector(optionalDirector.get());
        filial.setCompany(optionalCompany.get());
        filialRepository.save(filial);
        return new ApiResponse("Filial edited", true);
    }

    public ApiResponse deleteFilial(UUID id) {
        Optional<Filial> byId = filialRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("Filial not found", false);
        try {
            filialRepository.deleteById(id);
            return new ApiResponse("Filial deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Filial not deleted", false);
        }
    }
}










