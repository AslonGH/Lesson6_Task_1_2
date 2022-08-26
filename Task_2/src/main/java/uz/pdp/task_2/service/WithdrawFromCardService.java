package uz.pdp.task_2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.*;
import uz.pdp.task_2.entity.enums.BankNoteName;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.payload.WithdrawFromCardDto;
import uz.pdp.task_2.repository.BankATMRepository;
import uz.pdp.task_2.repository.CardRepository;
import uz.pdp.task_2.repository.EmployeeRepository;
import uz.pdp.task_2.repository.WithdrawMoneyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawFromCardService {

      @Autowired
      WithdrawMoneyRepository withdrawRepository;

      @Autowired
      CardRepository cardRepository;

      @Autowired
      BankATMRepository bankATMRepository;

      @Autowired
      JavaMailSender javaMailSender;

      @Autowired
      EmployeeRepository employeeRepository;

      public Result addWithdrawMoney(WithdrawFromCardDto withdrawDto)  {

           Optional<BankCard> optionalCard = cardRepository.findById(withdrawDto.getCardID());
           if (!optionalCard.isPresent())
           return new Result("Card not found",false);

           if (!optionalCard.get().isEnabled())
           return new Result("Card not enable",false);

           Optional<Atm> optionalATM = bankATMRepository.findById(withdrawDto.getBankomatID());
           if (!optionalATM.isPresent())
           return new Result("ATM not found",false);

           WithdrawFromCard withdrawMoney=new WithdrawFromCard();
           Atm bankATM = optionalATM.get();
           BankCard card = optionalCard.get();

           boolean own = (card.getBankName().getId()).equals(bankATM.getBank().getId());

           double balanceATMInDollar = bankATM.getCurrentBalanceInDollar();
           double balanceATMInSum =    bankATM.getCurrentBalanceInSum();

           double balanceCardInDollar = card.getBalanceInDollar();
           double balanceCardInSum = card.getBalanceInSum();


           double amountWithdraw  =     withdrawDto.getAmount()*withdrawDto.getBankNote();
           double commissionATMOwnCard     = bankATM.getCommissionWithdrawOwnCardInPercent()/100*amountWithdraw;
           double commissionATMForeignCard = bankATM.getCommissionWithdrawForeignCardInPercent()/100*amountWithdraw;

           boolean enoughSumInOwnCard=balanceCardInSum >= amountWithdraw+commissionATMOwnCard;
           boolean enoughSumInForeignCard=balanceCardInSum >= amountWithdraw+commissionATMForeignCard;

           boolean enoughDollarInOwnCard=balanceCardInDollar >= amountWithdraw+commissionATMOwnCard;
           boolean enoughDollarInForeignCard=balanceCardInDollar >= amountWithdraw+commissionATMForeignCard;

           boolean enoughDollarInATM=balanceATMInDollar >= amountWithdraw;
           boolean enoughSumInATM= balanceATMInSum >= amountWithdraw;

           double maxLimitDollar =   bankATM.getMaxWithdrawLimitDollar();
           double maxLimitSum =      bankATM.getMaxWithdrawLimitSum();


          if (withdrawDto.getBankNote() == 1000) {
              withdrawMoney.setBankNote(BankNoteName.MING_SUM.name());
          }
          else if (withdrawDto.getBankNote() == 5000)   {
              withdrawMoney.setBankNote(BankNoteName.BESH_MING_SUM.name());
          }
          else if (withdrawDto.getBankNote() == 10000)  {
              withdrawMoney.setBankNote(BankNoteName.ON_MING_SUM.name());
          }
          else if (withdrawDto.getBankNote() == 50000)  {
              withdrawMoney.setBankNote(BankNoteName.ELLIK_MING_SUM.name());
          }
          else if (withdrawDto.getBankNote() == 100000) {
              withdrawMoney.setBankNote(BankNoteName.YUZ_MING_SUM.name());
          }
          else if (withdrawDto.getBankNote() == 1)   {
              withdrawMoney.setBankNote(BankNoteName.BIR_DOLLAR.name());
          }
          else if (withdrawDto.getBankNote() == 5)   {
              withdrawMoney.setBankNote(BankNoteName.BESH_DOLLAR.name());
          }
          else if (withdrawDto.getBankNote() == 10)  {
              withdrawMoney.setBankNote(BankNoteName.ON_DOLLAR.name());
          }
          else if (withdrawDto.getBankNote() == 20)  {
              withdrawMoney.setBankNote(BankNoteName.YIGIRMA_DOLLAR.name());
          }
          else if (withdrawDto.getBankNote() == 50)  {
              withdrawMoney.setBankNote(BankNoteName.ELLIK_DOLLAR.name());
          }
          else if (withdrawDto.getBankNote() == 100) {
              withdrawMoney.setBankNote(BankNoteName.YUZ_DOLLAR.name());
          }
          else {
              return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                      "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
          }


           // MIJOZ KUPURA TANLAYDI VA NECHTALIGINI
           int [] arraySum=new int[]{ 1000, 5000, 10000, 50000, 100000 };
           int a=0;
           for (int i : arraySum) {
               if (i==withdrawDto.getBankNote()){
                   a++;
               }
           }
           int [] arrayDollar=new int[] {1,5,10,20,50,100};
           int b =0;
           for (int i : arrayDollar) {
               if (i==withdrawDto.getBankNote()){
                   b++;
               }
           }

          // AGAR ATM DA PUL KAM QOLSA SMS BORADI.
          Optional<Employee> byIdEmployee = employeeRepository.findById(bankATM.getCreatedBy());
          if (!byIdEmployee.isPresent()) return new Result("Employee not found",false);

             String email = byIdEmployee.get().getEmail();
          if (bankATM.getMinRestMoneyInSum().equals(balanceATMInSum)){ sendEMail(email);}
          if (bankATM.getMinRestMoneyInDollar().equals(balanceATMInDollar)){ sendEMail(email);}


          if (bankATM.getBudgetFromCommissionSum()==null) bankATM.setBudgetFromCommissionSum(0.00);
          if (bankATM.getBudgetFromCommissionDollar()==null) bankATM.setBudgetFromCommissionDollar(0.00);


             if (withdrawDto.getCurrency()==1 && a>0 &&  own ) {
           if (amountWithdraw>maxLimitSum) return new Result("max Limit for Withdraw: "+maxLimitSum, false);

              if (enoughSumInOwnCard && enoughSumInATM) {
                  bankATM.setCurrentBalanceInSum(balanceATMInSum - amountWithdraw + commissionATMOwnCard);
                  bankATM.setBudgetFromCommissionSum(bankATM.getBudgetFromCommissionSum()+commissionATMOwnCard);
                  card.setBalanceInSum(balanceCardInSum - amountWithdraw - commissionATMOwnCard);
              }
              else { return new Result("Balance of Card or ATM not enough", false); }
         }
        else if (withdrawDto.getCurrency()==1 && a>0 && !own)  {
         if (enoughSumInForeignCard && enoughSumInATM) {
          if (amountWithdraw>maxLimitSum) return new Result("max Limit for Withdraw: "+maxLimitSum, false);

           bankATM.setCurrentBalanceInSum(balanceATMInSum - amountWithdraw + commissionATMForeignCard);
           bankATM.setBudgetFromCommissionSum(bankATM.getBudgetFromCommissionSum()+commissionATMForeignCard);
           card.setBalanceInSum(balanceCardInSum - amountWithdraw - commissionATMForeignCard);
         } else { return new Result("Balance of Card or ATM not enough", false); }

     }

        else if (withdrawDto.getCurrency()==2 && b>0 &&  own)  {
         if (enoughDollarInOwnCard && enoughDollarInATM) {
          if (amountWithdraw>maxLimitDollar)return new Result("max Limit for Withdraw: "+maxLimitDollar, false);
           bankATM.setCurrentBalanceInDollar(balanceATMInDollar- amountWithdraw + commissionATMOwnCard);
           bankATM.setBudgetFromCommissionDollar(bankATM.getBudgetFromCommissionDollar()+commissionATMOwnCard);
           card.setBalanceInDollar(balanceCardInDollar-amountWithdraw-commissionATMOwnCard);
         }  else { return new Result("Balance of Card or ATM not enough", false); }
     }
        else if (withdrawDto.getCurrency()==2 && b>0 && !own)  {
           if (enoughDollarInForeignCard && enoughDollarInATM)
           {
             if (amountWithdraw>maxLimitDollar) return new Result("max Limit for Withdraw: "+maxLimitDollar, false);

               bankATM.setCurrentBalanceInDollar(balanceATMInDollar - amountWithdraw +commissionATMForeignCard);
               bankATM.setBudgetFromCommissionDollar(bankATM.getBudgetFromCommissionDollar()+commissionATMForeignCard);
               card.setBalanceInDollar(balanceCardInDollar-amountWithdraw-commissionATMForeignCard);

           } else { return new Result("Balance of Card or ATM not enough", false); }
     }
        else {
               return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                       "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
           }


          withdrawMoney.setAtm(bankATM);
          withdrawMoney.setBankCard(card);
          withdrawMoney.setAmount(withdrawDto.getAmount());
          withdrawMoney.setCurrency(withdrawDto.getCurrency());
          withdrawMoney.setDate(LocalDate.now());
          withdrawRepository.save(withdrawMoney);
           return new Result(" withdraw saved",true);
      }

      public Result editWithdrawMoneyById(WithdrawFromCardDto withdrawDto, Integer id) {

        Optional<WithdrawFromCard> optionalWithdraw = withdrawRepository.findById(id);
        if (!optionalWithdraw.isPresent())
            return new Result("Outcome not found",false);

        Optional<BankCard> optionalCard = cardRepository.findById(withdrawDto.getCardID());
        if (!optionalCard.isPresent())
            return new Result("Card not found",false);

        if (!optionalCard.get().isEnabled())
            return new Result("Card not enable",false);

        Optional<Atm> optionalATM = bankATMRepository.findById(withdrawDto.getBankomatID());
        if (!optionalATM.isPresent())
            return new Result("ATM not found",false);

        WithdrawFromCard withdrawMoney = optionalWithdraw.get();

        Atm bankATM = optionalATM.get();
        BankCard card = optionalCard.get();

        boolean own = (card.getBankName().getId()).equals(bankATM.getBank().getId());

        double balanceATMInDollar = bankATM.getCurrentBalanceInDollar();
        double balanceATMInSum = bankATM.getCurrentBalanceInSum();

        double balanceCardInDollar = card.getBalanceInDollar();
        double balanceCardInSum = card.getBalanceInSum();


          double amountWithdraw  = withdrawDto.getAmount()*withdrawDto.getBankNote();
        double commissionATMOwnCard      =bankATM.getCommissionWithdrawOwnCardInPercent()/100*amountWithdraw;
        double commissionATMForeignCard  = bankATM.getCommissionWithdrawForeignCardInPercent()/100*amountWithdraw;

        boolean enoughSumInOwnCard=balanceCardInSum >= amountWithdraw+commissionATMOwnCard;
        boolean enoughSumInForeignCard=balanceCardInSum >= amountWithdraw+commissionATMForeignCard;

        boolean enoughDollarInOwnCard=balanceCardInDollar >= amountWithdraw+commissionATMOwnCard;
        boolean enoughDollarInForeignCard=balanceCardInDollar >= amountWithdraw+commissionATMForeignCard;

        boolean enoughDollarInATM=balanceATMInDollar >= amountWithdraw;
        boolean enoughSumInATM= balanceATMInSum >= amountWithdraw;


          double maxLimitDollar =   bankATM.getMaxWithdrawLimitDollar();
          double maxLimitSum =      bankATM.getMaxWithdrawLimitSum();


          if (withdrawDto.getBankNote() == 1000)        {
            withdrawMoney.setBankNote(BankNoteName.MING_SUM.name());
        }
        else if (withdrawDto.getBankNote() == 5000)   {
            withdrawMoney.setBankNote(BankNoteName.BESH_MING_SUM.name());
        }
        else if (withdrawDto.getBankNote() == 10000)  {
            withdrawMoney.setBankNote(BankNoteName.ON_MING_SUM.name());
        }
        else if (withdrawDto.getBankNote() == 50000)  {
            withdrawMoney.setBankNote(BankNoteName.ELLIK_MING_SUM.name());
        }
        else if (withdrawDto.getBankNote() == 100000) {
            withdrawMoney.setBankNote(BankNoteName.YUZ_MING_SUM.name());
        }
        else if (withdrawDto.getBankNote() == 1)    {
            withdrawMoney.setBankNote(BankNoteName.BIR_DOLLAR.name());
        }
        else if (withdrawDto.getBankNote() == 5)    {
            withdrawMoney.setBankNote(BankNoteName.BESH_DOLLAR.name());
        }
        else if (withdrawDto.getBankNote() == 10)   {
            withdrawMoney.setBankNote(BankNoteName.ON_DOLLAR.name());
        }
        else if (withdrawDto.getBankNote() == 20)   {
            withdrawMoney.setBankNote(BankNoteName.YIGIRMA_DOLLAR.name());
        }
        else if (withdrawDto.getBankNote() == 50)   {
            withdrawMoney.setBankNote(BankNoteName.ELLIK_DOLLAR.name());
        }
        else if (withdrawDto.getBankNote() == 100)  {
            withdrawMoney.setBankNote(BankNoteName.YUZ_DOLLAR.name());
        }
        else {
            return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                    "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
        }

          //SÖM
        int [] arraySum=new int[]{ 1000, 5000, 10000, 50000, 100000 };
        int a=0;
        for (int i : arraySum) {
            if (i==withdrawDto.getBankNote()){
                a++;
            }
        }

        // DOLLAR
        int [] arrayDollar=new int[] {1,5,10,20,50,100};
        int b =0;
        for (int i : arrayDollar) {
            if (i==withdrawDto.getBankNote()){
                b++;
            }
        }

          // AGAR ATM DA PUL KAM QOLSA SMS BORADI.
          Optional<Employee> byIdEmployee = employeeRepository.findById(bankATM.getCreatedBy());
          if (!byIdEmployee.isPresent()) return new Result("Employee not found",false);

          String email = byIdEmployee.get().getEmail();
          if (bankATM.getMinRestMoneyInSum().equals(balanceATMInSum)){ sendEMail(email);}
          if (bankATM.getMinRestMoneyInDollar().equals(balanceATMInDollar)){ sendEMail(email);}


          if (bankATM.getBudgetFromCommissionSum()==null) bankATM.setBudgetFromCommissionSum(0.00);
          if (bankATM.getBudgetFromCommissionDollar()==null) bankATM.setBudgetFromCommissionDollar(0.00);

               if (withdrawDto.getCurrency()==1 && a>0 && own ) {
              if (enoughSumInOwnCard && enoughSumInATM) {
                  if (amountWithdraw>maxLimitSum) return new Result("max Limit for Withdraw: "+maxLimitSum, false);
                  bankATM.setCurrentBalanceInSum(balanceATMInSum - amountWithdraw + commissionATMOwnCard);
                  bankATM.setBudgetFromCommissionSum(bankATM.getBudgetFromCommissionSum()+commissionATMOwnCard);
                  card.setBalanceInSum(balanceCardInSum - amountWithdraw - commissionATMOwnCard);
              }
              else { return new Result("Balance of Card or ATM not enough", false); }

          }
          else if (withdrawDto.getCurrency()==1 && a>0 && !own) {
              if (enoughSumInForeignCard && enoughSumInATM) {
                  if (amountWithdraw>maxLimitSum) return new Result("max Limit for Withdraw: "+maxLimitSum, false);
                  bankATM.setCurrentBalanceInSum(balanceATMInSum - amountWithdraw + commissionATMForeignCard);
                  bankATM.setBudgetFromCommissionSum(bankATM.getBudgetFromCommissionSum()+commissionATMForeignCard);
                  card.setBalanceInSum(balanceCardInSum - amountWithdraw - commissionATMForeignCard);
              }
              else { return new Result("Balance of Card or ATM not enough", false); }

          }
          else if (withdrawDto.getCurrency()==2 && b>0 && own)  {
              if (enoughDollarInOwnCard && enoughDollarInATM) {
                  if (amountWithdraw>maxLimitDollar)return new Result("max Limit for Withdraw: "+maxLimitDollar, false);
                  bankATM.setCurrentBalanceInDollar(balanceATMInDollar- amountWithdraw + commissionATMOwnCard);
                  bankATM.setBudgetFromCommissionDollar(bankATM.getBudgetFromCommissionDollar()+commissionATMOwnCard);
                  card.setBalanceInDollar(balanceCardInDollar-amountWithdraw-commissionATMOwnCard);
              }
              else { return new Result("Balance of Card or ATM not enough", false); }
          }
          else if (withdrawDto.getCurrency()==2 && b>0 && !own) {
              if (enoughDollarInForeignCard && enoughDollarInATM) {
                  if (amountWithdraw>maxLimitDollar)return new Result("max Limit for Withdraw: "+maxLimitDollar, false);
                  bankATM.setCurrentBalanceInDollar(balanceATMInDollar - amountWithdraw +commissionATMForeignCard);
                  bankATM.setBudgetFromCommissionDollar(bankATM.getBudgetFromCommissionDollar()+commissionATMForeignCard);
                  card.setBalanceInDollar(balanceCardInDollar-amountWithdraw-commissionATMForeignCard);
              }  else { return new Result("Balance of Card or ATM not enough", false); }
          }
          else {
              return new Result("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                      "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
          }


             try {
            withdrawMoney.setDate(LocalDate.parse(withdrawDto.getDate()));
        }
          catch (Exception e) {
            return new Result("enter  date(yyyy-MM-dd.)",false);
        }
        withdrawMoney.setAtm(bankATM);
        withdrawMoney.setBankCard(card);
        withdrawMoney.setAmount(withdrawDto.getAmount());
        withdrawMoney.setCurrency(withdrawDto.getCurrency());

        withdrawRepository.save(withdrawMoney);
        return new Result("Withdraw edited",true);
    }


      public List<WithdrawFromCard> getWithdrawMoney(){ return withdrawRepository.findAll();}

      public WithdrawFromCard getWithdrawMoneyById(Integer id){
            Optional<WithdrawFromCard> optionalOutcome = withdrawRepository.findById(id);
            return optionalOutcome.orElseGet(WithdrawFromCard::new);
      }

      public ApiResponse getDailyWithdrawFromCardByATMId(Integer id, String day) {
        LocalDate date = null;
        try {
            date=LocalDate.parse(day);
        } catch (Exception e) {
            new Result("enter  date(yyyy-MM-dd.)",false);
        }
        List<WithdrawFromCard> allByAtmIdAndDate = withdrawRepository.findAllByAtmIdAndDate(id, date);
        return new ApiResponse(" ",true, allByAtmIdAndDate);
        }



      public Result deleteWithdrawMoney(Integer id){
            try {
                  withdrawRepository.deleteById(id);
                  return new Result("Outcome deleted",true);
            }catch (Exception e){
                  return new Result("Outcome not deleted",false);
            }

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

