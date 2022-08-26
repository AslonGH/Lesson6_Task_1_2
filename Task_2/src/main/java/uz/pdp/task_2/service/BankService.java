package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.Address;
import uz.pdp.task_2.entity.Banks;
import uz.pdp.task_2.payload.BankDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.repository.AddressRepository;
import uz.pdp.task_2.repository.BankATMRepository;
import uz.pdp.task_2.repository.BanksRepository;

import java.util.Optional;

@Service
public class BankService {


      @Autowired
      BanksRepository bankRepository;

      @Autowired
      AddressRepository addressRepository;

      @Autowired
      BankATMRepository bankATMRepository;


      public Result addBank(BankDto bankDto) {

            Optional<Address> bankAddress = addressRepository.findById(bankDto.getAddressId());
            if (!bankAddress.isPresent()) return new Result("Address not found",false);

              Banks bank=new Banks();
            bank.setAddress(bankAddress.get());
            bank.setName(bankDto.getName());
            bankRepository.save(bank);
            return new Result("Bank saved",true);
      }

      public Result editBankById(BankDto bankDto, Integer id) {

            Optional<Banks> byIdBank = bankRepository.findById(id);
            if (!byIdBank.isPresent())
                  return new Result("Bank not found",false);

            Optional<Address> bankAddress = addressRepository.findById(bankDto.getAddressId());
            if (!bankAddress.isPresent()) return new Result("Address not found",false);

              Banks bank=byIdBank.get();
            bank.setAddress(bankAddress.get());
            bank.setName(bankDto.getName());
            bankRepository.save(bank);
            return new Result("Bank edited",true);
      }

}




 /*    Set<Atm> atmSet =new HashSet<>();
            List<UUID> bankomatATMs = bankDto.getBankomatATMs();
            for (UUID bankomatATM : bankomatATMs) {
                  Optional<Atm> byIdBankomat = bankATMRepository.findById(bankomatATM);
                  if (!byIdBankomat.isPresent())
                        return new Result("Bankomat not found",false);
                  atmSet.add(byIdBankomat.get());
            }
            */
 /*  Set<Atm> atmSet =new HashSet<>();
            List<UUID> bankomatATMs = bankDto.getBankomatATMs();
            for (UUID bankomatATM : bankomatATMs) {
                  Optional<Atm> byIdBankomat = bankATMRepository.findById(bankomatATM);
                  if (!byIdBankomat.isPresent())
                        return new Result("Bankomat not found",false);
             atmSet.add(byIdBankomat.get());
            }*/