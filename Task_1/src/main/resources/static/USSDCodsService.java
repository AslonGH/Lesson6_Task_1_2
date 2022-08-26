

/*
package uz.pdp.cascade_types_annotatsiyalar.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.Package;
import uz.pdp.cascade_types_annotatsiyalar.entity.*;
import uz.pdp.cascade_types_annotatsiyalar.entity.enumClass.UssdCods;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.UssdCodsName;
import uz.pdp.cascade_types_annotatsiyalar.payload.USSDCodsDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class USSDCodsService {

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    InfosEntertainmentRepository entertainmentRepository;

    @Autowired
    UssdCodesRepository ussdCodesRepository;


    //  private static final Integer CHECK_TARIFF=1;
    //  private static final Integer CHANGE_TARIFF=2;
    //  private static final Integer ACTIVATE_TARIFF=3;
    //  private static final Integer BUY_PACKAGE=4;
    //  private static final Integer BUY_INFO_ENTERTAINMENT=5;
    //  DETAILINGDA BÖLADI BULAR
    //  SEND_SMS=1, CALL=2, USE_INTERNET=3, USER_INFO_ENTERTAINMENT=4


    // DB GA SAQLASH
    public ApiResponse addUSSDCods(USSDCodsDto ussdCodsDto) {

        UssdCods ussdCods = new UssdCods();

        List<Integer> serviceCods = ussdCodsDto.getServiceCods();
        for (Integer serviceCod : serviceCods)
        {
            switch (serviceCod) {
                case 1:
                    ussdCods.setCodeName(UssdCodsName.CHECK_TARIFF);
                    break;
                case 2:
                    ussdCods.setCodeName(UssdCodsName.CHANGE_TARIFF);
                    break;
                case 3:
                    ussdCods.setCodeName(UssdCodsName.ACTIVATE_TARIFF);
                    break;
                case 4:
                    ussdCods.setCodeName(UssdCodsName.BUY_PACKAGE);
                    break;
                case 5:
                    ussdCods.setCodeName(UssdCodsName.BUY_INFO_ENTERTAINMENT);
                    break;
                default:
                    return new ApiResponse("CHECK_TARIFF=1;\n" +
                            "CHANGE_TARIFF=2;\n" +
                            "BUY_PACKAGE=3;\n" +
                            "BUY_INFO_ENTERTAINMENT=4;", false);
            }
        }
        ussdCodesRepository.save(ussdCods);
            return new ApiResponse("SAVED", true);
        }

    public ApiResponse getUSSDCods() {
        List<UssdCods> all = ussdCodesRepository.findAll();
        return new ApiResponse("ussdCods",true,all);
    }

    public ApiResponse editUSSDCods(UUID id, USSDCodsDto ussdCodsDto) {

        Optional<UssdCods> optionalUSSDCods = ussdCodesRepository.findById(id);
        if (!optionalUSSDCods.isPresent())
            return new ApiResponse("ussdCods not found",false);

        UssdCods ussdCods = optionalUSSDCods.get();

        List<Integer> serviceCods = ussdCodsDto.getServiceCods();
        for (Integer serviceCod : serviceCods)
        {
            switch (serviceCod) {
                case 1:
                    ussdCods.setCodeName(UssdCodsName.CHECK_TARIFF);
                    break;
                case 2:
                    ussdCods.setCodeName(UssdCodsName.CHANGE_TARIFF);
                    break;
                case 3:
                    ussdCods.setCodeName(UssdCodsName.BUY_PACKAGE);
                    break;
                case 4:
                    ussdCods.setCodeName(UssdCodsName.BUY_INFO_ENTERTAINMENT);
                    break;
                default:
                    return new ApiResponse("CHECK_TARIFF=1;\n" +
                            "CHANGE_TARIFF=2;\n" +
                            "BUY_PACKAGE=3;\n" +
                            "BUY_INFO_ENTERTAINMENT=4;", false);
            }
        }
        ussdCodesRepository.save(ussdCods);
        return new ApiResponse("edited", true);
    }

    public ApiResponse serviceUSSDCods(USSDCodsDto ussdCodsDto) {
           List<Integer> serviceCods = ussdCodsDto.getServiceCods();

          for (Integer serviceCod : serviceCods) {
              switch (serviceCod) {
                  // DASTURGA KIRGAN CUSTOMER SIMCARTASINING TARIFFI
                  case 1:
                      Optional<Tariff> tariff = tariffRepository.findBySimCards_CustomerId(getCustomerId());
                      return tariff.map(value -> new ApiResponse("tariff  found", true, value))
                              .orElseGet(() -> new ApiResponse("tariff not found", false));

                  // DASTURGA KIRGAN CUSTOMER SIMCARTASINING TARIFFINI ÖZAGARTIRADI
                  case 2:
                      Optional<Tariff> tariffById = tariffRepository.findById(ussdCodsDto.getTariffUUID());
                      if (!tariffById.isPresent()) return new ApiResponse("tariff not found", false);

                      Optional<SimCard> opSimCard = simCardRepository.findByCustomerId(getCustomerId());
                      if (!opSimCard.isPresent()) return new ApiResponse("SimCard not found", false);
                      SimCard simCard = opSimCard.get();
                      if (simCard.getEnabled() && simCard.getBalance() >= tariffById.get().getPrice()) {
                          simCard.setTariff(tariffById.get());
                          simCard.setTariffLastActiveDay(LocalDate.now());
                          simCard.setBalance(simCard.getBalance() - (tariffById.get().getPrice()+tariffById.get().getTransitionPrice()));
                          simCardRepository.save(simCard);
                      } else {
                          return new ApiResponse("Balance of SimCard saved not enough", false);
                      }
                      return new ApiResponse("tariff  saved", true);

                  // DASTURGA KIRGAN CUSTOMER SIMCARTASINING TARIFFINI AKTILASHTIRADI
                  case 3:
                      Optional<Tariff> tariffByCustId = tariffRepository.findById(ussdCodsDto.getTariffUUID());
                      if (!tariffByCustId.isPresent()) return new ApiResponse("tariff not found", false);

                      Optional<SimCard> opSimCardC = simCardRepository.findByCustomerId(getCustomerId());
                      if (!opSimCardC.isPresent()) return new ApiResponse("SimCard not found", false);
                      SimCard simCardC = opSimCardC.get();

                      int diff = LocalDate.now().lengthOfMonth() - LocalDate.now().getDayOfMonth();  // OY TUGASHIGACHA QOLGAN KUN
                      double priceProDay = tariffByCustId.get().getPrice() / tariffByCustId.get().getDeadLine();
                      double oyOxirgachaMablag = diff * priceProDay; // Bu hamma xizmat/ uchun umumiy narx

                      // TARIFNING AMAL QILISH MUDDATI BUGUN TIGAGAN BÖLSA
                      if (Period.between(simCardC.getTariff().getExpireDate(), LocalDate.now()).getDays() == 0)
                      {
                          if (simCardC.getEnabled() && simCardC.getBalance() >= oyOxirgachaMablag) {
                              simCardC.setBalance(simCardC.getBalance() - oyOxirgachaMablag);
                              simCardC.setTariffLastActiveDay(LocalDate.now());
                              simCardC.getTariff().setExpireDate(LocalDate.now().plusDays(diff));
                          }
                          simCardRepository.save(simCardC);

                      } else {
                          return new ApiResponse("Balance of SimCard saved not enough", false);
                      }
                          return new ApiResponse("tariff  saved", true);

                  //  DASTURGA KIRGAN CUSTOMER SIMCARTASINING PACKAGE
                  //  MASALAN MIJOZ  PAKET TURI SMS BÖLGAN PAKETNI SOTIB OLMOQCHI,AGAR SOTIB OLAYOTGAN PAKETI QOLGAN QOLDIQQA QÖSHILSA
                  //  ÖZINING MUDDATI ÖTMAGAN SMS PAKETINI PAKETINI PAKETREPOSITORIDAN QIDIRADI VA CHAQIRGAN PAKETINI USTIGA QÖSHADI
                  //  AKS HOLDA YANGI UUID BILAN SAVE QILADI.
                  case 4:
                      List<UUID> packageID = ussdCodsDto.getPackageIDs();
                      Set<Package> packages = new HashSet<>();
                      for (UUID uuid : packageID) {
                          Optional<Package> aPackage = packageRepository.findById(uuid);
                          if (!aPackage.isPresent()) return new ApiResponse("package not found", false);
                          packages.add(aPackage.get());
                      }
                      Optional<SimCard> opSimCard1 = simCardRepository.findByCustomerId(getCustomerId());
                      if (!opSimCard1.isPresent()) return new ApiResponse("SimCard not found", false);
                      SimCard simCard1 = opSimCard1.get();

                      Set<Package> simCardPackages = simCard1.getAPackage();

                      for (Package aPackageSimCard : simCardPackages)  // SIMCARTANING ÖZINING PACKAGE
                      {
                          for (Package aPackage : packages)    // TASHQARIDAN BERILGAN PACKAGE
                          {

                              double lastAmount = aPackage.getAmount();
                              LocalDate dayOfBuyLastPackage = aPackage.getDayOfPackageSold();

                              LocalDate endOfPackage = dayOfBuyLastPackage.plusDays((long) aPackage.getValidityDays());
                              LocalDate endOfPackage1 = dayOfBuyLastPackage.plusDays((long) aPackage.getValidityDays() + 1);

                              // QÖSHISH MUMKIN VA AMAL QILISH MUDDATI TUGAMAGAN
                              if (aPackage.getIsAddToRestOffPackage() && aPackage.getTypeOfPackage().equals(aPackageSimCard.getTypeOfPackage())
                                      && (Period.between(endOfPackage, LocalDate.now()).getDays() > 0)) {
                                  lastAmount += aPackage.getAmount();
                                  aPackage.setAmount(lastAmount);
                                  packageRepository.save(aPackage);

                                  // QÖSHISH MUMKIN  VA AMAL QILISH MUDDATI TUGADI
                              } else if (aPackage.getIsAddToRestOffPackage() && (Period.between(endOfPackage1, LocalDate.now()).getDays() == 0)) {
                                  lastAmount = aPackage.getAmount(); // BU holda uni nolga teng bölib qoladi
                                  aPackage.setAmount(lastAmount);
                                  packageRepository.save(aPackage);

                                  // QÖSHISH MUMKIN EMAS VA AMAL QILISH MUDDATI TUGADI
                              } else if (!aPackage.getIsAddToRestOffPackage() && (Period.between(endOfPackage1, LocalDate.now()).getDays() == 0)) {
                                  // lastAmount=aPackage.getAmount()-lastAmount;
                                  simCardPackages.add(aPackage);
                                  simCardRepository.save(simCard1);
                              }

                          }
                      }
                      return new ApiResponse("package  saved", true);


                  // DASTURGA KIRGAN CUSTOMER SIMCARTASINING BUY_INFO_ENTERTAINMENT
                  case 5:
                      List<UUID> entertainments = ussdCodsDto.getEntertainments();
                      Set<InfosEntertainment> infosEntertainments = new HashSet<>();
                      for (UUID uuid : entertainments) {
                          Optional<InfosEntertainment> entertainment = entertainmentRepository.findById(uuid);
                          if (!entertainment.isPresent()) return new ApiResponse("package not found", false);
                          infosEntertainments.add(entertainment.get());
                      }
                      Optional<SimCard> opSimCard2 = simCardRepository.findByCustomerId(getCustomerId());
                      if (!opSimCard2.isPresent()) return new ApiResponse("SimCard not found", false);
                      SimCard simCard2 = opSimCard2.get();
                      simCard2.setEntertainments(infosEntertainments);
                      return new ApiResponse("Entertainments  saved", true);
                  // break;
                  default:
                      return new ApiResponse("CHECK_TARIFF=1;\n" +
                              "CHANGE_TARIFF=2;\n" +
                              "BUY_PACKAGE=3;\n" +
                              "BUY_INFO_ENTERTAINMENT=4;", false);
              }
          }        return new ApiResponse("saved", true);
    }

    // TIZIM GA KIRIB TURGAN EMPLOYEE
    public UUID getCustomerId(){
        EmpCustomer principal = (EmpCustomer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

}



*/
/*
    Tariff tariff1 = simCardC.getTariff();
    tariff1.getSms()==0||tariff1.getMinuteBetweenExternSet()==0||tariff1.getMinuteBetweenInternSet()==0)
    if (simCardC.isEnabled() && simCardC.getBalance() >= oyOxirgachaMablag){
    if (tariff1.getMb()==0){
    tariff1.setMb(ussdCodsDto.getMb());
    simCardC.setTariff(tariffByCustId.get());
*/

