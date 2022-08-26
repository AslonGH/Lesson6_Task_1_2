/*
package uz.pdp.task_2.servoce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.*;
import uz.pdp.task_2.entity.enomClass.BankNote;
import uz.pdp.task_2.entity.enoms.BankNoteName;
import uz.pdp.task_2.payload.ATMBudgetDto;
import uz.pdp.task_2.payload.Resolt;
import uz.pdp.task_2.repository.*;

import java.util.*;

@Service
public class ATMBudgetService {


      @Autowired
      BanknoteRepository banknoteRepository;

      @Autowired
      ATMBudgetRepository atmBudgetRepository;


      public Resolt addATMBudget(ATMBudgetDto bankomatDto) {

              AtmBudget atmBudget=new AtmBudget();

         List<BankNote> bankNotes = new ArrayList<>();

                 if (bankomatDto.getBankNote() == 1000)   {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.MING_SUM));

            }
            else if (bankomatDto.getBankNote() == 5000)   {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.BESH_MING_SUM));
            }
            else if (bankomatDto.getBankNote() == 10000)  {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.ON_MING_SUM));
            }
            else if (bankomatDto.getBankNote() == 50000)  {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.ELLIK_MING_SUM));
            }
            else if (bankomatDto.getBankNote() == 100000) {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.YUZ_MING_SUM));
            }
            else if (bankomatDto.getBankNote() == 1)      {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.BIR_DOLLAR));
            }
            else if (bankomatDto.getBankNote() == 5)      {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.BESH_DOLLAR));
            }
            else if (bankomatDto.getBankNote() == 10)     {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.ON_DOLLAR));
            }
            else if (bankomatDto.getBankNote() == 20)     {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.YIGIRMA_DOLLAR));
            }
            else if (bankomatDto.getBankNote() == 50)     {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.ELLIK_DOLLAR));
            }
            else if (bankomatDto.getBankNote() == 100)    {
                bankNotes.add(banknoteRepository.findByBankNoteName(BankNoteName.YUZ_DOLLAR));
            }
            else {
                  return new Resolt("Banknote not found. Choose 1000,5000,10000,50000,100000 for Sum " +
                          "And 1,5,10,20,50,100 for Dollar And Choose 1 for Sum, 2 for Dollar Currency ", false);
            }

         Map<String,Integer>map=new HashMap<>();

          Set<Integer> bankNotes1 = new HashSet<>();
          // MAP ISHLATSA HAM BÖLADI

          int [] arraySum=new int[] { 1000, 5000, 10000, 50000, 100000 };
          int a=0;
          int bn=0;

          for (int i : arraySum) {
              if (i==bankomatDto.getBankNote()){
                  bankNotes1.add(bankomatDto.getBankNote());
                  a++;
              }
         }


          // DOLLAR
          int [] arrayDollar=new int[] {1,5,10,20,50,100};
          int b =0;
          for (int i : arrayDollar) {
              if (i==bankomatDto.getBankNote()){
                  b++;
              }
          }


          atmBudget.setBankNotes(bankNotes);
          atmBudget.setCurrentBalanceInSum(bankomatDto.getCurrentBalanceInSum());
          atmBudget.setCurrentBalanceInDollar(bankomatDto.getCurrentBalanceInDollar());

          atmBudgetRepository.save(atmBudget);

            return new Resolt("Bankomat saved",true);
      }


      public ApiResponse getBankATMBudgetByAtmId(Integer id) {
            AtmBudget byAtmId = atmBudgetRepository.findByAtmId(id);
            return new ApiResponse("Bankomat ",true,byAtmId);
      }


}

*/
