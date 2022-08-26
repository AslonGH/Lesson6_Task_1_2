package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.Atm;
import uz.pdp.task_2.entity.BankCard;
import uz.pdp.task_2.entity.DepositInATM;
import uz.pdp.task_2.entity.enums.BankNoteName;
import uz.pdp.task_2.payload.DepositATMDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.repository.BankATMRepository;
import uz.pdp.task_2.repository.CardRepository;
import uz.pdp.task_2.repository.DepositATMRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class DepositInATMService {

      @Autowired
      DepositATMRepository depositATMRepository;

      @Autowired
      BankATMRepository bankATMRepository;

      @Autowired
      CardRepository cardRepository;



      public Result addIncome(DepositATMDto depositDto){

          Optional<Atm> byId = bankATMRepository.findById(depositDto.getBankomatID());
          if (!byId.isPresent())  return new Result("ATM not found",false);

          Optional<BankCard> byId1 = cardRepository.findById(depositDto.getFromCardId());
          if (!byId1.isPresent())  return new Result("Card not found",false);


             Atm bankATM = byId.get();

          double balanceInSum = bankATM.getCurrentBalanceInSum();
          double balanceInDollar = bankATM.getCurrentBalanceInDollar();

            DepositInATM depositInATM=new DepositInATM();

              if (depositDto.getBankNote() == 1000)    {
              depositInATM.setBankNote(BankNoteName.MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 5000)   {
              depositInATM.setBankNote(BankNoteName.BESH_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 10000)  {
              depositInATM.setBankNote(BankNoteName.ON_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 50000)  {
              depositInATM.setBankNote(BankNoteName.ELLIK_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 100000) {
              depositInATM.setBankNote(BankNoteName.YUZ_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 1)      {
              depositInATM.setBankNote(BankNoteName.BIR_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 5)      {
              depositInATM.setBankNote(BankNoteName.BESH_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 10)     {
              depositInATM.setBankNote(BankNoteName.ON_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 20)     {
              depositInATM.setBankNote(BankNoteName.YIGIRMA_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 50)     {
              depositInATM.setBankNote(BankNoteName.ELLIK_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 100)    {
              depositInATM.setBankNote(BankNoteName.YUZ_DOLLAR.name());
          }
          else {
              return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
              "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
          }

          // PUL QÖYISHDA BERILGAN SUMMANI KUPURA/GA BÖLISH YOKI MIJOZ va EMPLOYEE KUPURA TANLAYDI VA NECHTALIGINI
           int [] arraySum=new int[]{ 1000, 5000, 10000, 50000, 100000 };
           int a=0;
           for (int i : arraySum) {
              if (i==depositDto.getBankNote()){
                  a++;
              }
           }

           // DOLLAR
           int [] arrayDollar=new int[] {1,5,10,20,50,100};
           int b =0;
           for (int i : arrayDollar) {
              if (i==depositDto.getBankNote()){
                  b++;
              }
           }


               if (depositDto.getCurrency()==1 && a>0) {
             bankATM.setCurrentBalanceInSum(balanceInSum+(depositDto.getAmount()*depositDto.getBankNote()));
          }
          else if (depositDto.getCurrency()==2 && b>0) {
              bankATM.setCurrentBalanceInDollar(balanceInDollar+(depositDto.getAmount()*depositDto.getBankNote()));
          }
          else {
              return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
              "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
          }

          depositInATM.setBankomat(bankATM);
          depositInATM.setAmount(depositDto.getAmount());
          depositInATM.setBankCard(byId1.get());

          try {
                 depositInATM.setDate(LocalDate.parse(depositDto.getDate()));
             } catch (Exception e) {
                 return new Result("enter  date(yyyy-MM-dd.)",false);
             }
           depositATMRepository.save(depositInATM);
           return new Result("Deposit saved ",true);
      }

      public List<DepositInATM> getIncomes() {
              return depositATMRepository.findAll();
        }



      public List<DepositInATM> getAllFillMoneyByATMId(Integer id) {
          return depositATMRepository.findAllByBankomatId(id);
      }

      public Result editIncomeById(DepositATMDto depositDto, Integer id) {

           Optional<DepositInATM> optionalDepositInATM = depositATMRepository.findById(id);
           if (!optionalDepositInATM.isPresent())
               return new Result("DepositInATM not found", false);

           Optional<Atm> byId = bankATMRepository.findById(depositDto.getBankomatID());
           if (!byId.isPresent())
               return new Result("ATM not found",false);

          Optional<BankCard> byId1 = cardRepository.findById(depositDto.getFromCardId());
          if (!byId1.isPresent())  return new Result("Card not found",false);

                 Atm bankATM = byId.get();
              double balanceInSum = bankATM.getCurrentBalanceInSum();
              double balanceInDollar = bankATM.getCurrentBalanceInDollar();
            DepositInATM depositInATM = optionalDepositInATM.get();

          if (depositDto.getBankNote() == 1000) {
              depositInATM.setBankNote(BankNoteName.MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 5000) {
              depositInATM.setBankNote(BankNoteName.BESH_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 10000) {
              depositInATM.setBankNote(BankNoteName.ON_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 50000) {
              depositInATM.setBankNote(BankNoteName.ELLIK_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 100000) {
              depositInATM.setBankNote(BankNoteName.YUZ_MING_SUM.name());
          }
          else if (depositDto.getBankNote() == 1) {
              depositInATM.setBankNote(BankNoteName.BIR_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 5) {
              depositInATM.setBankNote(BankNoteName.BESH_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 10) {
              depositInATM.setBankNote(BankNoteName.ON_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 20) {
              depositInATM.setBankNote(BankNoteName.YIGIRMA_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 50) {
              depositInATM.setBankNote(BankNoteName.ELLIK_DOLLAR.name());
          }
          else if (depositDto.getBankNote() == 100) {
              depositInATM.setBankNote(BankNoteName.YUZ_DOLLAR.name());
          }
          else {
              return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                      "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
          }

            // SÖM
           int [] arraySum=new int[]{ 1000, 5000, 10000, 50000, 100000 };
           int a=0;
           for (int i : arraySum) {
               if (i==depositDto.getBankNote()){
                   a++;
               }
           }

           // DOLLAR
           int [] arrayDollar=new int[] {1,5,10,20,50,100};
           int b =0;
           for (int i : arrayDollar) {
               if (i==depositDto.getBankNote()){
                   b++;
               }
           }

                if (depositDto.getCurrency()==1 && a>0) {
               bankATM.setCurrentBalanceInSum(balanceInSum+(depositDto.getAmount()*depositDto.getBankNote()));
           }
           else if (depositDto.getCurrency()==2 && b>0) {
               bankATM.setCurrentBalanceInDollar(balanceInDollar+(depositDto.getAmount()*depositDto.getBankNote()));
           }
           else {
               return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                       "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
           }

           depositInATM.setBankomat(byId.get());
           // PUL QÖYISHDA BERILGAN SUMMANI KUPURA/GA BÖLISH YOKI MIJOZ KUPURA TANLAYDI VA NECHTALIGINI
           depositInATM.setAmount(depositDto.getAmount());

           try {
               depositInATM.setDate(LocalDate.parse(depositDto.getDate()));
           } catch (Exception e) {
               return new Result("enter  date(yyyy-MM-dd.)",false);
           }
           depositInATM.setBankCard(byId1.get());
           depositATMRepository.save(depositInATM);
           return new Result("Deposit edited ",true);


      }

      public Result deleteIncome(Integer id) {
            try {
                  depositATMRepository.deleteById(id);
                  return new Result("Income deleted", true);
            } catch (Exception e) {
                  return new Result("Income not deleted", false);
            }

      }
}






