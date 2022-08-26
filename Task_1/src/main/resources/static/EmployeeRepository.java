/*   // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI SIMCARD ning PCKAGE/ NING QOLDIQ/ I:
      Optional<Package> opSMSPackage = packageRepository.findByTariffId_SimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.SMS.name());
     if (!opSMSPackage.isPresent())
     return new ApiResponse("package not found", false);

     Optional<Package> opMINUTEPackage = packageRepository.findByTariffId_SimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.MINUTE.name());
     if (!opMINUTEPackage.isPresent())
     return new ApiResponse("package not found", false);

     Optional<Package> opMBPackage = packageRepository.findByTariffId_SimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.MB.name());
     if (!opMBPackage.isPresent())
     return new ApiResponse("package not found", false);
*/
/*    //TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD Ixtiyoridagi minut package
        //  List<Package> opSMSPackage = packageRepository.findBySimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.SMS.name());
        //  double amountMinutePackage = 0;

        List<Package> opMINUTEPackage = packageRepository.findBySimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.MINUTE.name());
        for (Package aPackage : opMINUTEPackage) {
            amountMinutePackage = amountMinutePackage+ aPackage.getAmount();
        }

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD Ixtiyoridagi mb package
         //  double amountMBPackage =0;
        List<Package> opMBPackage = packageRepository.findBySimCardsId_CustomerIdAndTypeOfPackage(getCustomerUUId(), ServiceName.MB.name());
        for (Package aPackage : opMBPackage) {
            amountMBPackage=amountMBPackage+ aPackage.getAmount();
        }
        */
/*
            Optional<Package> optionalPackageActual = packageRepository.findByTypeOfPackageAndId(actionDto.getTypeOfPackage(),
                    simCardCall.getId());
            if (!optionalPackageActual.isPresent()) {
                return new ApiResponse("This package not found " + optionalPackageActual, false);
            }
            Package packageOfSimCard = optionalPackageActual.get();

               if (aPackageToBuy.getIsAddToRestOffPackage()) {
                          packageOfSimCard.setAmount(packageOfSimCard.getAmount() + aPackageToBuy.getAmount());
                          packageRepository.save(packageOfSimCard);
                      }
                      else {
                          Set<Package> aPackage = simCardCall.getAPackage();
                          aPackage.add(aPackageToBuy);
                      }
   */
/*public ApiResponse editAction(UUID id, ActionDto actionDto) {

        Optional<UseServices> optionalAction = actionRepository.findById(id);
        if (!optionalAction.isPresent())
            return new ApiResponse("Action not found",false);

            Optional<SimCard> optionalSimCard = simCardRepository.findById(actionDto.getSimCardCallToUUID());
            if (!optionalSimCard.isPresent())
                return new ApiResponse("SimCard with UUID " + (optionalSimCard) + " not found", false);

        UseServices action = optionalAction.get();
        switch (actionDto.getArtOfAction()){
            case 1: action.setArtOfAction(UssdCodsName.SEND_SMS.name());break;
            case 2: action.setArtOfAction(UssdCodsName.CALL.name());break;
            case 3: action.setArtOfAction(UssdCodsName.USE_INTERNET.name());break;
            case 4: action.setArtOfAction(UssdCodsName.USER_INFO_ENTERTAINMENT.name());break;

            default:return new ApiResponse("SEND_SMS=1, CALL=2, USE_INTERNET=3, " +
                    " USER_INFO_ENTERTAINMENT=4",false);
        }
        action.setSimCard(optionalSimCard.get());
        action.setLocalDate(LocalDate.now());
        actionRepository.save(optionalAction.get());
        return new ApiResponse("Detailing saved",true);
    }*/
/*
package uz.pdp.cascade_types_annotatsiyalar.repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.Employee;
import uz.pdp.cascade_types_annotatsiyalar.entity.enumClass.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByEmailNot(String email);

    // OPTIONAL ISHLATAMIZ CHUNKI BIZGA User TIPIDAGI OBJECT QAYTARSIN. Parametr/ni örni muhim
    Optional<Employee> findByEmailCodeAndEmail(String emailCode, String email);

    Optional<Employee> findByEmail(String email);

    Set<Employee>findByRoles(Set<Role> roles);

}
*/
