package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.TourniquetCard;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.ServiceName;
import uz.pdp.cascade_types_annotatsiyalar.payload.TourniquetCardDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.CompanyRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.TourniquetCardRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourniquetCardService {

    @Autowired
    TourniquetCardRepository tourniquetCardRepository;
    @Autowired
    CompanyRepository  companyRepository;
    @Autowired
    EmpCustomerRepository empCustomerRepository;


    public ApiResponse addTourniquetCard(TourniquetCardDto turnKetDto) {

        Optional<Company> optionalCompany = companyRepository.findById(turnKetDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("company topilmadi", false);

        Optional<EmpCustomer> optionalEmployee = empCustomerRepository.findById(turnKetDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("employee topilmadi", false);

          TourniquetCard turnKet=new TourniquetCard();

        turnKet.setExpireDate(LocalDate.parse(turnKetDto.getExpireDate()));
        turnKet.setCompany(optionalCompany.get());
        turnKet.setEmpCustomer(optionalEmployee.get());

      if (Period.between(LocalDate.parse(turnKetDto.getExpireDate()), LocalDate.now()).getDays() >=0){
           turnKet.setStatus(ServiceName.ACTUAL.name());
       }
      else if (Period.between(LocalDate.now(),LocalDate.parse(turnKetDto.getExpireDate())).getDays()<0){
           turnKet.setStatus(ServiceName.EXPIRED.name());
      }

      tourniquetCardRepository.save(turnKet);
      return new ApiResponse("Successful saved",true);
    }

    public ApiResponse editTourniquetCard(UUID id, TourniquetCardDto turnKetDto) {

         Optional<TourniquetCard> optionalTurnKet = tourniquetCardRepository.findById(id);
        if (!optionalTurnKet.isPresent()) {
            return new ApiResponse("turniket topilmadi", false); }

         Optional<Company> optionalCompany = companyRepository.findById(turnKetDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("company topilmadi", false);

         Optional<EmpCustomer> optionalEmployee = empCustomerRepository.findById(turnKetDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("employee topilmadi", false);

          TourniquetCard turnKet = optionalTurnKet.get();

         turnKet.setExpireDate(LocalDate.parse(turnKetDto.getExpireDate()));
         turnKet.setCompany(optionalCompany.get());
         turnKet.setEmpCustomer(optionalEmployee.get());

        if (Period.between(LocalDate.parse(turnKetDto.getExpireDate()), LocalDate.now()).getDays() >=0)
        {  turnKet.setStatus(ServiceName.ACTUAL.name()); }
        else if (Period.between(LocalDate.now(),LocalDate.parse(turnKetDto.getExpireDate())).getDays()<0)
        {  turnKet.setStatus(ServiceName.EXPIRED.name()); }

        tourniquetCardRepository.save(turnKet);
        return new ApiResponse("Successful edited", true);
    }

    public ApiResponse getTourniquetCardById(UUID id) {

        Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(id);

        // AGAR optionalTourniquetCard  OBJECT BÖLSA true va tourniquetCard ;  null yoki empty bölsa, false qaytaramiz
        return optionalTourniquetCard.map( tourniquetCard -> new ApiResponse("TurnKet found", true,tourniquetCard)
        ).orElseGet( () -> new ApiResponse("TurnKet not found", false));
    }

    public ApiResponse getTourniquetCards() {
        List<TourniquetCard> all = tourniquetCardRepository.findAll();
        return new ApiResponse(all,"tourniquetCards",true);
    }

    public ApiResponse deleteTourniquetCard(UUID id) {

        Optional<TourniquetCard> optionalTurnKet = tourniquetCardRepository.findById(id);
        if (optionalTurnKet.isPresent())
            try {
                tourniquetCardRepository.deleteById(id);
                return new ApiResponse("TurnKet öchirildi", true);
            } catch (Exception e) {
                return new ApiResponse("TurnKet öchirilmadi", false);
            }
        return new ApiResponse("TurnKet topilmadi", false);
    }

}

