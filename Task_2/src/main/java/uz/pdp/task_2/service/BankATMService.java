package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.*;
import uz.pdp.task_2.entity.enumClass.CardType;
import uz.pdp.task_2.entity.enums.CardTypeName;
import uz.pdp.task_2.payload.BankATMDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.repository.*;

import java.util.*;

@Service
public class BankATMService {

      @Autowired
      CardRepository cardRepository;

      @Autowired
      CardTypeRepository cardTypeRepository;

      @Autowired
      BanksRepository bankRepository;

      @Autowired
      AddressRepository addressRepository;

      @Autowired
      BankATMRepository bankATMRepository;

      @Autowired
      JavaMailSender javaMailSender;

      @Autowired
      EmployeeRepository employeeRepository;

      @Autowired
      DepositATMRepository depositATMRepository;

      @Autowired
      DepositCardRepository depositCardRepository;

      @Autowired
      WithdrawMoneyRepository withdrawMoneyRepository;



      public Result addBankomat(BankATMDto bankomatDto) {

            Optional<Banks> byId = bankRepository.findById(bankomatDto.getBankID());
            if (!byId.isPresent())
                  return new Result("Bank not found",false);

            Optional<Address> byIdAddress = addressRepository.findById(bankomatDto.getAddressID());
            if (!byIdAddress.isPresent())
                  return new Result("Address not found",false);

                    Atm bankATM=new Atm();

            bankATM.setBank(byId.get());
            bankATM.setAddress(byIdAddress.get());
            bankATM.setMaxWithdrawLimitDollar(bankomatDto.getMaxWithdrawLimitDollar());
            bankATM.setMaxWithdrawLimitSum(bankomatDto.getMaxWithdrawLimitSum());

            bankATM.setCurrentBalanceInSum(bankomatDto.getCurrentBalanceInSum());
            bankATM.setCurrentBalanceInDollar(bankomatDto.getCurrentBalanceInDollar());
            bankATM.setMinRestMoneyInSum(bankomatDto.getMinRestMoneyInSum());
            bankATM.setMinRestMoneyInDollar(bankomatDto.getMinRestMoneyInDollar());

            bankATM.setCommissionDepositForeignCardInPercent(bankomatDto.getCommissionDepositForeignCardInPercent());
            bankATM.setCommissionDepositOwnCardInPercent(bankomatDto.getCommissionDepositOwnCardInPercent());
            bankATM.setCommissionWithdrawForeignCardInPercent(bankomatDto.getCommissionWithdrawForeignCardInPercent());
            bankATM.setCommissionWithdrawOwnCardInPercent(bankomatDto.getCommissionWithdrawOwnCardInPercent());
            bankATM.setBudgetFromCommissionSum(bankomatDto.getBudgetFromCommissionSum());
            bankATM.setBudgetFromCommissionDollar(bankomatDto.getBudgetFromCommissionDollar());

             Set<CardType> forCardsList = new HashSet<>();
            List<Integer> forCards = bankomatDto.getCardTypesFor();
            for (Integer forCard : forCards) {
                  if (forCard == 1) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.UZCARD));
                  } else if (forCard == 2) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.HUMO));
                  } else if (forCard == 3) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.VISA));
                  } }
            bankATM.setCardTypesFor(forCardsList);
            bankATMRepository.save(bankATM);
            return new Result("Bankomat saved",true);
      }

      public Result getBankomatById(Integer id) {
            Optional<Atm> optionalBankomat = bankATMRepository.findById(id);
            if (!optionalBankomat.isPresent())
            return new Result("Bankomat not found",false);
            return new Result("Bankomat ",true,optionalBankomat.get());
      }

      public ApiResponse getBankATMBudgetById(Integer id) {

            Optional<Atm> byId = bankATMRepository.findById(id);
            if (!byId.isPresent())
            return new ApiResponse("Bankomat not found", false);

        // BANKOMATGA EMPLOYEE QÖYGAN BankNote LAR
       List<DepositInATM> depositInATMList = depositATMRepository.findAllByBankomatId(id);
       List<String> depositList = new ArrayList<>();
        for (DepositInATM depositInATM : depositInATMList) {
              depositList.add(depositInATM.getBankNote());
        }
        String [] array=new String[depositInATMList.size()];
        depositList.toArray(array);
        Arrays.sort(array);
        Map<String, Integer> depAtm = new HashMap<>();
        for (int i = 0; i < array.length-1; i++) {
                  int count=1;
                  for (int j = i+1; j < array.length; j++) {
                        if (array[i].equals(array[j])) count++;
                        else break;
                  }
                  if (count>1){
                        depAtm.put("Xodim solgan banknotlar: "+array[i]+" banknotlar soni", count);
                        i+=(count-1);
                  }
            }

        // BANKOMATGA CUSTOMER QÖYGAN BankNote LAR
       List<DepositInCard> depositInCardList = depositCardRepository.findAllByAtmId(id);
       List<String> depositCardList = new ArrayList<>();
        for (DepositInCard depositInCard : depositInCardList) {
              depositCardList.add(depositInCard.getBankNote());
        }
        String [] arrayCard=new String[depositCardList.size()];
        depositCardList.toArray(arrayCard);
        Arrays.sort(arrayCard);
        Map<String, Integer> depCardMap = new HashMap<>();
        for (int i = 0; i < arrayCard.length-1; i++) {
                  int count=1;
                  for (int j = i+1; j < arrayCard.length; j++) {
                        if (arrayCard[i].equals(arrayCard[j])) count++;
                        else break; }
                  if (count>1){
                        depCardMap.put("Mijoz solgan banknotlar: "+arrayCard[i] + " banknotlar SONI", count);
                        i+=(count-1);
                  }
            }

         // BANKOMATDAN CUSTOMER OLGAN BankNote LAR
        List<WithdrawFromCard> withdrawCard = withdrawMoneyRepository.findAllByAtmId(id);
       List<String> withdrawCarList = new ArrayList<>();
        for (WithdrawFromCard withFCard : withdrawCard) {
              withdrawCarList.add(withFCard.getBankNote());
        }
        String [] arrayWithDr=new String[withdrawCarList.size()];
        withdrawCarList.toArray(arrayWithDr);
        Arrays.sort(arrayWithDr);
        Map<String, Integer> withCardMap = new HashMap<>();
        for (int i = 0; i < arrayWithDr.length-1; i++) {
                  int count=1;
                  for (int j = i+1; j < arrayWithDr.length; j++) {
                        if (arrayWithDr[i].equals(arrayWithDr[j])) count++;
                        else break;

                  }
                  if (count>1){
                   withCardMap.put("Mijoz olgan banknotlar: "+arrayWithDr[i]+ " banknotlar SONI: ", count);
                   i+=(count-1);
                  }
            }
       Atm atm = byId.get();
      return new ApiResponse(depAtm, depCardMap, withCardMap, atm.getCurrentBalanceInSum(), atm.getCurrentBalanceInDollar());

}

      public Result editBankomatById(BankATMDto bankomatDto, Integer id) {

            Optional<Atm> byIdBankomat = bankATMRepository.findById(id);
            if (!byIdBankomat.isPresent())
                  return new Result("Bankomat not found", false);

            Optional<Banks> byId = bankRepository.findById(bankomatDto.getBankID());
            if (!byId.isPresent())
                  return new Result("Bnk not found", false);

            Optional<Address> byIdAddress = addressRepository.findById(bankomatDto.getAddressID());
            if (!byIdAddress.isPresent())
                  return new Result("Address not found", false);

              Atm bankATM = byIdBankomat.get();
            bankATM.setBank(byId.get());
            bankATM.setAddress(byIdAddress.get());
            bankATM.setMaxWithdrawLimitDollar(bankomatDto.getMaxWithdrawLimitDollar());
            bankATM.setMaxWithdrawLimitSum(bankomatDto.getMaxWithdrawLimitSum());
            bankATM.setCurrentBalanceInSum(bankomatDto.getCurrentBalanceInSum());
            bankATM.setCurrentBalanceInDollar(bankomatDto.getCurrentBalanceInDollar());
            bankATM.setMinRestMoneyInSum(bankomatDto.getMinRestMoneyInSum());
            bankATM.setMinRestMoneyInDollar(bankomatDto.getMinRestMoneyInDollar());

            bankATM.setCommissionDepositForeignCardInPercent(bankomatDto.getCommissionDepositForeignCardInPercent());
            bankATM.setCommissionDepositOwnCardInPercent(bankomatDto.getCommissionDepositOwnCardInPercent());
            bankATM.setCommissionWithdrawForeignCardInPercent(bankomatDto.getCommissionWithdrawForeignCardInPercent());
            bankATM.setCommissionWithdrawOwnCardInPercent(bankomatDto.getCommissionWithdrawOwnCardInPercent());

            bankATM.setBudgetFromCommissionSum(bankomatDto.getBudgetFromCommissionSum());
            bankATM.setBudgetFromCommissionDollar(bankomatDto.getBudgetFromCommissionDollar());


              Set<CardType> forCardsList = new HashSet<>();
            List<Integer> forCards = bankomatDto.getCardTypesFor();

            for (Integer forCard : forCards) {
                  if (forCard == 1) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.UZCARD));
                  } else if (forCard == 2) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.HUMO));
                  } else if (forCard == 3) {
                        forCardsList.add(cardTypeRepository.findByCardTypeName(CardTypeName.VISA));
                  }
            }   bankATM.setCardTypesFor(forCardsList);

            // AGAR ATM DA PUL KAM QOLSA SMS BORADI.
            Optional<Employee> byIdEmployee = employeeRepository.findById(bankATM.getCreatedBy());
            if (!byIdEmployee.isPresent()) return new Result("Employee not found",false);

             String email = byIdEmployee.get().getEmail();
            if (bankATM.getMinRestMoneyInSum().equals(bankATM.getCurrentBalanceInSum())){ sendEMail(email);}
            if (bankATM.getMinRestMoneyInDollar().equals(bankATM.getCurrentBalanceInDollar())){ sendEMail(email);}

            bankATMRepository.save(bankATM);
            return new Result("Bankomat edited", true);
      }

      public Result deleteBankomat(Integer id){
            try {
                  bankATMRepository.deleteById(id);
                  return new Result("Bankomat deleted",true);
            }catch (Exception e){
                  return new Result("Bankomat not deleted",false);
            }
      }

      public List<Atm> getBankATMs() {
            return bankATMRepository.findAll();
      }

      public Boolean sendEMail(String sendingEmail){
            try {
                  SimpleMailMessage mailMessage = new SimpleMailMessage();
                  mailMessage.setFrom("aslon.dinov@gmail.com");
                  mailMessage.setTo(sendingEmail);
                  mailMessage.setSubject("minimum level !!!");
                  mailMessage.setText("minimum level of money reached");
                  javaMailSender.send(mailMessage);
                  return true;

            }catch (Exception e){
                  return false;
            }
      }

}

