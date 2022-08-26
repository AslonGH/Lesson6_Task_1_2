package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.payload.CompanyBudgetDto;
import uz.pdp.cascade_types_annotatsiyalar.entity.Package;
import uz.pdp.cascade_types_annotatsiyalar.entity.*;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.ServiceName;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.UssdCodsName;
import uz.pdp.cascade_types_annotatsiyalar.payload.UseServicesDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.DetailingDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.PaymentsDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UseServicesService {

    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    UseServicesRepository actionRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    DetailingService detailingService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    InfosEntertainmentRepository entertainmentRepository;
    @Autowired
    CompanyBudgetService budgetService;


    // TIZIM GA KIRIB TURGAN Role
    public UUID getCustomerUUId(){
        EmpCustomer authentication = (EmpCustomer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authentication.getId();
    }

    public ApiResponse addAction(UseServicesDto actionDto) {

       Optional<SimCard> opSimCardCall = simCardRepository.findByCustomerId(getCustomerUUId());
    if (!opSimCardCall.isPresent())return new ApiResponse("SimCard with UUID "+(opSimCardCall)+" not found",false);

    if (!opSimCardCall.get().getEnabled())return new ApiResponse("SimCard not active "+opSimCardCall.get(),false);

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI SIMCARD i
        SimCard simCardFrom = opSimCardCall.get();

     // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI SimCard enable (YAROQLI, RÖYXATDAN ÖTGAN) balance minusda va mijos pul solmoqchi
     // emas va minus yurishi mumkin bölmasa
     if (simCardFrom.getEnabled() && simCardFrom.getBalance() <= 0 &&
            actionDto.getArtOfAction() != 5 && !simCardFrom.getCanBeInMinusBalance() ){
        return new ApiResponse("SimCard is not active fill your balance", false);
    }
     else if (!simCardFrom.getEnabled()){
        return new ApiResponse("SimCard with this  "+simCardFrom.getId()+"  is not enable, not active", false);
    }

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD Ixtiyoridagi sms package
        double amountSMSPackage =0;
        double amountMinutePackage = 0;
        double amountMBPackage =0;

        Set<Package> aPackageSets = simCardFrom.getPackages();
        for (Package aPackage : aPackageSets) {
            if (aPackage.getTypeOfPackage().equals(ServiceName.SMS.name())){
                amountSMSPackage=amountSMSPackage+aPackage.getAmount();
                aPackage.setAmount(0.00);
            }
            else if (aPackage.getTypeOfPackage().equals(ServiceName.MINUTE.name())) {
                amountMinutePackage = amountMinutePackage+ aPackage.getAmount();
                aPackage.setAmount(0.00);
            }
            else if (aPackage.getTypeOfPackage().equals(ServiceName.MB.name())) {
                amountMBPackage=amountMBPackage+ aPackage.getAmount();
                aPackage.setAmount(0.00);
            }
        }

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning Balance
        double balance = simCardFrom.getBalance();

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning tariff
        Tariff tariff = simCardFrom.getTariff();

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning tariff ixtiyoridagi  SMS LAR SONI va narx PRO SMS
        int sms = tariff.getSms();
        double priceForSms = tariff.getPriceForSms();
        double priceOneSms=priceForSms/sms;

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning tariff ixtiyoridagi MinuteBetweenInternSet  LAR SONI va narx Pro Minute
        double minuteBetweenInternSet = tariff.getMinuteBetweenInternSet();
        double priceForMinuteBetweenInternSet = tariff.getPriceForMinuteBetweenInternSet();
        double priceOneCallIntern=priceForMinuteBetweenInternSet/minuteBetweenInternSet;

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning tariff ixtiyoridagi MinuteBetweenExternSet LAR SONI va narx Pro Minute
        double minuteBetweenExternSet = tariff.getMinuteBetweenExternSet();
        double priceForMinuteBetweenExternSet = tariff.getPriceForMinuteBetweenExternSet();
        double priceOneCallExtern=priceForMinuteBetweenExternSet/minuteBetweenExternSet;

        // TIZIMGA KIRGAN CUSTOMERGA TEGISHLI simCARD ning tariff ixtiyoridagi MB LAR SONI va narx Pro Minute
        double mb = tariff.getMb();
        double priceForMb = tariff.getPriceForMb();
        double priceForOneMB=priceForMb/mb;


              UseServices action=new UseServices();

             if (actionDto.getArtOfAction() == 1) {
            action.setArtOfAction(UssdCodsName.SEND_SMS.name());
            detailingService.addDetailing(new DetailingDto(1, simCardFrom.getId(), LocalDate.now()));

            if (tariff.getSms() >= actionDto.getAmountSMS()) {      // AGAR TARIFFNI ÖZIDA tekin SMS YETARLI BÖLSA
                sms = tariff.getSms() - actionDto.getAmountSMS();
                tariff.setSms(sms);
                simCardFrom.setTariff(tariff);

            }else if (tariff.getSms() < actionDto.getAmountSMS() && amountSMSPackage >= actionDto.getAmountSMS()){
                amountSMSPackage = amountSMSPackage - actionDto.getAmountSMS();

                  // SMS Package/ga tegishli Jami SMS hajmi  amountSMSPackage
                  for (Package aPackage : aPackageSets) {
                       if (aPackage.getTypeOfPackage().equals(ServiceName.SMS.name())){
                           aPackage.setAmount(amountSMSPackage);
                           break;
                       }
                 }

            } else {
                balance = balance - (priceOneSms * actionDto.getAmountSMS());
                simCardFrom.setBalance(balance);
                budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.SEND_SMS.name(),
                                 priceOneSms * actionDto.getAmountSMS(),LocalDate.now()));
            }
            simCardRepository.save(simCardFrom);
        }
        else if (actionDto.getArtOfAction() == 2) {
            action.setArtOfAction(UssdCodsName.CALL.name());
            detailingService.addDetailing(new DetailingDto(2, simCardFrom.getId(), LocalDate.now()));

                 // TIZIMGA KIRGAN CUSTOMER CALL QILAYOTGAN simCARD
                 Optional<SimCard> optionalSimCard = simCardRepository.findById(actionDto.getSimCardCallToUUID());
                 if (!optionalSimCard.isPresent())
                     return new ApiResponse("SimCard with UUID " + (optionalSimCard) + " not found", false);

                 // CALL QILINAYOTGAN simCARD
                 SimCard simCardCallTo = optionalSimCard.get();

                 // True ya'ni InternSet bölsa internCall
                 boolean isCallIntern = simCardFrom.getCompanyCode().equals(simCardCallTo.getCompanyCode());

            // INTERNCALL
            if (isCallIntern)
            {
                // AGAR TARIFFNI ÖZIDA tekin SMS YETARLI BÖLSA
                if (tariff.getMinuteBetweenInternSet() >= actionDto.getDurationCallInMinute()) {
                    minuteBetweenInternSet = minuteBetweenInternSet - actionDto.getDurationCallInMinute();
                    tariff.setMinuteBetweenInternSet(minuteBetweenInternSet);
                    simCardFrom.setTariff(tariff);

                }
                   // AGAR TARIFFDAGI YETMASA PACKAGEDAN OLLAMIZ
                 else if (tariff.getMinuteBetweenInternSet() < actionDto.getDurationCallInMinute() &&
                        amountMinutePackage >= actionDto.getDurationCallInMinute())
                {
                      // MINUTE Package/ga tegishli Jami MINUT hajmi  amountMinutePackage
                     amountMinutePackage = amountMinutePackage - actionDto.getDurationCallInMinute();

                     // QOLGAN MINUTNI YANA MinutePackage ga qöshib qöyamiz
                      for (Package aPackage : aPackageSets) {
                          if (aPackage.getTypeOfPackage().equals(ServiceName.MINUTE.name())){
                            aPackage.setAmount(amountMinutePackage);
                            break;
                          }
                      }
                } else {

                    balance = balance - priceOneCallIntern * actionDto.getDurationCallInMinute();
                    simCardFrom.setBalance(balance);
                    budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.CALL.name(),
                            priceOneCallIntern * actionDto.getDurationCallInMinute(),LocalDate.now()));
                }


            } else
            {             // EXTERNCALL

                // AGAR TARIFFNI ÖZIDA tekin SMS YETARLI BÖLSA
                if (tariff.getMinuteBetweenExternSet() >= actionDto.getDurationCallInMinute()) {
                    minuteBetweenExternSet = minuteBetweenExternSet - actionDto.getDurationCallInMinute();
                    tariff.setMinuteBetweenExternSet(minuteBetweenExternSet);
                    simCardFrom.setTariff(tariff);
                }
                // AGAR TARIFFDAGI YETMASA PACKAGEDAN OLLAMIZ
                else if (tariff.getMinuteBetweenExternSet() < actionDto.getDurationCallInMinute() &&
                        amountMinutePackage >= actionDto.getDurationCallInMinute()) {

                    // MINUTE Package/ga tegishli Jami MINUT hajmi  amountMinutePackage
                    amountMinutePackage = amountMinutePackage - actionDto.getDurationCallInMinute();

                    // QOLGAN MINUTNI YANA MinutePackage ga qöshib qöyamiz
                    for (Package aPackage : aPackageSets) {
                         if (aPackage.getTypeOfPackage().equals(ServiceName.MINUTE.name())){
                            aPackage.setAmount(amountMinutePackage);
                            break;
                         }
                     }
                }
                else {
                    balance = balance - priceOneCallExtern * actionDto.getDurationCallInMinute();
                    simCardFrom.setBalance(balance);
                    budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.CALL.name(),
                            priceOneCallExtern * actionDto.getDurationCallInMinute(),LocalDate.now()));
                }
            }
                 simCardRepository.save(simCardFrom);
        }
        else if (actionDto.getArtOfAction() == 3) {
            action.setArtOfAction(UssdCodsName.USE_INTERNET.name());
            detailingService.addDetailing(new DetailingDto(3, simCardFrom.getId(), LocalDate.now()));
            // AGAR TARIFFNI ÖZIDA tekin SMS YETARLI BÖLSA
            if (mb >= actionDto.getSpentInternetInMB()) {
                mb = mb - actionDto.getSpentInternetInMB();
                tariff.setMb(mb);
                simCardFrom.setTariff(tariff);
            }
            // AGAR TARIFFDAGI YETMASA PACKAGEDAN OLLAMIZ
            else if (mb < actionDto.getSpentInternetInMB() && amountMBPackage >= actionDto.getSpentInternetInMB()) {
                    amountMBPackage = amountMBPackage - actionDto.getSpentInternetInMB();
                    for (Package aPackage : aPackageSets) {
                        if (aPackage.getTypeOfPackage().equals(ServiceName.MB.name())){
                        aPackage.setAmount(amountMBPackage);
                        break;
                    }
                }
            }
            else {
                balance = balance - priceForOneMB * actionDto.getSpentInternetInMB();
                simCardFrom.setBalance(balance);
                budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.USE_INTERNET.name(),
                        priceForOneMB * actionDto.getSpentInternetInMB(),LocalDate.now()));
            }
            simCardRepository.save(simCardFrom);
        }
        else if (actionDto.getArtOfAction() == 4) {
            action.setArtOfAction(UssdCodsName.BUY_PACKAGE.name());
            detailingService.addDetailing(new DetailingDto(4, simCardFrom.getId(), LocalDate.now()));

            Package aPackageToBuy =null;
            switch (actionDto.getTypeOfPackage()){
            case 1:
            Optional<Package> optionalPackageToBy = packageRepository.findByTypeOfPackageAndId(ServiceName.SMS.name(),actionDto.getPackageUUID());
            if (!optionalPackageToBy.isPresent()){return new ApiResponse("This package not found " + optionalPackageToBy, false);}
            aPackageToBuy=optionalPackageToBy.get();
            break;

            case 2:
            Optional<Package> optionalPackageToBy1 = packageRepository.findByTypeOfPackageAndId(ServiceName.MB.name(),actionDto.getPackageUUID());
            if (!optionalPackageToBy1.isPresent()){return new ApiResponse("This package not found " + optionalPackageToBy1, false);}
            aPackageToBuy=optionalPackageToBy1.get();
            break;

            case 3:
            Optional<Package> optionalPackageToBy2 = packageRepository.findByTypeOfPackageAndId(ServiceName.MINUTE.name(),actionDto.getPackageUUID());
            if (!optionalPackageToBy2.isPresent()){return new ApiResponse("This package not found " + optionalPackageToBy2, false);}
            aPackageToBuy=optionalPackageToBy2.get();
            break;
            default : return new ApiResponse("1- SMS, 2- MB, 3- MINUTE", false);
            }

            if (aPackageToBuy.getIsPackageSold() && aPackageToBuy.getIsPackageSold() != null)
            return new ApiResponse("This Package is already active "+aPackageToBuy, false);

            if(!simCardFrom.getTariff().getId().equals(aPackageToBuy.getTariff().getId()))
            return new ApiResponse("You can't buy this package with Id '"+ aPackageToBuy.getId()+"' You have another Tariff ", false);

             if ((simCardFrom.getBalance() >= aPackageToBuy.getPrice()) || simCardFrom.getCanBeInMinusBalance())
            {
                Set<Package> aPackages = simCardFrom.getPackages();

                 // SOTIB OLINADIGAN PACKAGE QOLDIQQA QÖSHILSA
                 if (aPackageToBuy.getAddToRestOffPackage())
                 {
                    for (Package aPackage : aPackages)
                    {
                        if (aPackage.getTypeOfPackage().equals(aPackageToBuy.getTypeOfPackage()))
                        {
                            aPackage.setAmount( aPackage.getAmount() + aPackageToBuy.getAmount() );
                            break;
                        }
                    }

                 // SOTIB OLINADIGAN PACKAGE QOLDIQQA QÖSHILMASA,ALOHIDA PACKAGE QILIB SIMCARD NING packages IGA QÖSHAMIZ
                 }  else {
                     aPackages.add(aPackageToBuy);
                     simCardFrom.setPackages(aPackages);
                 }

                aPackageToBuy.setDayOfPackageSold(LocalDate.now());
                aPackageToBuy.setIsPackageSold(true);
                balance =  balance - aPackageToBuy.getPrice();
                simCardFrom.setBalance(balance);
                budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.BUY_PACKAGE.name(),
                                                        aPackageToBuy.getPrice(),LocalDate.now()));
                simCardRepository.save(simCardFrom);
             }  else  {
                  return new ApiResponse("Balance of SimCard not enough", false);
                }
        }
        else if (actionDto.getArtOfAction() == 5) {
            action.setArtOfAction(UssdCodsName.FILL_BALANCE.name());
            detailingService.addDetailing(new DetailingDto(5, simCardFrom.getId(), LocalDate.now()));
            paymentService.addPayment(new PaymentsDto(3, simCardFrom.getId(), LocalDate.now()));

            balance = balance + (actionDto.getAmountToFillBalance());
            simCardFrom.setBalance(balance);
            simCardRepository.save(simCardFrom);
        }
        else if (actionDto.getArtOfAction() == 6) {
            action.setArtOfAction(UssdCodsName.CHECK_TARIFF.name());
            detailingService.addDetailing(new DetailingDto(6, simCardFrom.getId(), LocalDate.now()));
            action.setSimCard(simCardFrom);
            action.setLocalDate(LocalDate.now());
            actionRepository.save(action);
            return new ApiResponse("Action saved",true, simCardFrom.getTariff().getName());

        }
        else if (actionDto.getArtOfAction() == 7) {
         action.setArtOfAction(UssdCodsName.ACTIVATE_TARIFF.name());
         Tariff tariffSimCard = simCardFrom.getTariff();
         if (tariffSimCard.getIsActive()) return new ApiResponse("Tariff is active", true);
         double tariffPrice= tariffSimCard.getPrice()+tariffSimCard.getTransitionPrice();
         if (simCardFrom.getEnabled() && ( simCardFrom.getBalance() >= tariffPrice || simCardFrom.getCanBeInMinusBalance()) )
         {
            simCardFrom.setTariffLastActiveDay(LocalDate.now());
            simCardFrom.setBalance( simCardFrom.getBalance()-tariffPrice );
            tariff.setIsActive(true);
            // tariffRepository.save(tariffSimCard);
            detailingService.addDetailing(new DetailingDto(7, simCardFrom.getId(), LocalDate.now()));
            simCardRepository.save(simCardFrom);
            budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.ACTIVATE_TARIFF.name(),tariffPrice,LocalDate.now()));
          } else {
                return new ApiResponse("Balance of SimCard not enough", false);
            }
        }
        else if (actionDto.getArtOfAction() == 8) {

            Optional<Tariff> optionalTariff = tariffRepository.findById(actionDto.getTariffID());
          if (!optionalTariff.isPresent()) return new ApiResponse("Tariff not found",false);

           // SIM CARD NIG TARIFF REJASI
           Tariff tariffNew = optionalTariff.get();

          if (tariffNew.getIsActive() && tariffNew.getIsActive() != null)
          return new ApiResponse("This Tariff is already active "+tariffNew.getName(), false);

          double priceNewTariff=tariffNew.getPrice()+tariffNew.getTransitionPrice();
          if( simCardFrom.getEnabled() &&(simCardFrom.getBalance() >= priceNewTariff || simCardFrom.getCanBeInMinusBalance()) )
          {
              simCardFrom.setTariff(optionalTariff.get());
              simCardFrom.setTariffLastActiveDay(LocalDate.now());
              simCardFrom.setBalance(simCardFrom.getBalance()-priceNewTariff );
              tariffNew.setIsActive(true);
              tariffRepository.save(tariffNew);
              simCardRepository.save(simCardFrom);
              action.setArtOfAction(UssdCodsName.CHANGE_TARIFF.name());
              detailingService.addDetailing(new DetailingDto(8, simCardFrom.getId(), LocalDate.now()));
              budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.CHANGE_TARIFF.name(),
                                                                                    priceNewTariff,LocalDate.now()));
          }
          else { return new ApiResponse("Balance of SimCard not enough",false); }

          if (Period.between(tariffNew.getExpireDate(), LocalDate.now()).getDays()<=0){tariffNew.setIsActive(false);};
        }
        else if (actionDto.getArtOfAction() == 9) {

          Optional<InfosEntertainment> optionalEntertainment = entertainmentRepository.findById(actionDto.getEntertainmentID());
         if (!optionalEntertainment.isPresent()){
         return new ApiResponse("This infosEntertainment not found "+optionalEntertainment, false);}

         InfosEntertainment infosEntertainment = optionalEntertainment.get();
         if (infosEntertainment.getIsActive()&& infosEntertainment.getIsActive() != null)
         return new ApiResponse("This Entertainment is already active "+infosEntertainment.getName(), false);

         double price= infosEntertainment.getPriceProTypeOfPeriod()*infosEntertainment.getDeadline();
           if( simCardFrom.getEnabled() && ( simCardFrom.getBalance() >= price || simCardFrom.getCanBeInMinusBalance()) )
           {
               Set<InfosEntertainment> entertainments = simCardFrom.getEntertainments();
              entertainments.add(infosEntertainment);
              infosEntertainment.setIsActive(true);
              simCardFrom.setBalance(simCardFrom.getBalance() - price );
              simCardFrom.setEntertainments(entertainments);

              action.setArtOfAction(UssdCodsName.BUY_INFO_ENTERTAINMENT.name());
              detailingService.addDetailing(new DetailingDto(7, simCardFrom.getId(), LocalDate.now()));
              simCardRepository.save(simCardFrom);
              budgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.BUY_INFO_ENTERTAINMENT.name(), price, LocalDate.now()));
           }else {
             return new ApiResponse("Balance of SimCard not enough",false); }

      }
        else  {
            return new ApiResponse("SEND_SMS=1, CALL=2, USE_INTERNET=3, " +
                    " USER_INFO_ENTERTAINMENT=4....", false);
           }

        action.setSimCard(simCardFrom);
        action.setLocalDate(LocalDate.now());

        actionRepository.save(action);
        return new ApiResponse("Action saved",true);

    }

    public ApiResponse getAction() {
        List<UseServices> all = actionRepository.findAll();
        return new ApiResponse("Detailing: ",true,all);
    }

}



