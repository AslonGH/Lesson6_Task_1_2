package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.Banks;
import uz.pdp.task_2.entity.BankCard;
import uz.pdp.task_2.entity.enums.CardTypeName;
import uz.pdp.task_2.payload.BankCardDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.repository.BanksRepository;
import uz.pdp.task_2.repository.CardRepository;
import uz.pdp.task_2.repository.CardTypeRepository;
import uz.pdp.task_2.repository.RoleRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


@Service
public class CardService {


      @Autowired
      CardRepository cardRepository;

      @Autowired
      CardTypeRepository cardTypeRepository;

      @Autowired
      BanksRepository bankRepository;

      @Autowired
      RoleRepository roleRepository;



      public Result addCard(BankCardDto cardDto){

             Optional<Banks> byId = bankRepository.findById(cardDto.getBankId());
            if (!byId.isPresent())
            return new Result("Bank not found",false);


             boolean exists = cardRepository.existsByCVVCode3(cardDto.getCVVCode_3());
            if (exists)
            return new Result("Such CVVCode already exist",false);

             boolean existsByParole4 = cardRepository.existsByParole4(cardDto.getParole_4());
            if (existsByParole4)
            return new Result("Such Parole already exist",false);

             boolean existsByNumber16 = cardRepository.existsByNumber16(cardDto.getNumber_16());
            if (existsByNumber16)
            return new Result("Such Number already exist",false);


              BankCard card=new BankCard();

            card.setFirstnameCustomer(cardDto.getFirstname());
            card.setLastnameCustomer(cardDto.getLastname());
            card.setBalanceInSum(cardDto.getBalanceInSum());
            card.setBalanceInDollar(cardDto.getBalanceInDollar());
            card.setBankName(byId.get());
            card.setNumber16(cardDto.getNumber_16());
            card.setCVVCode3(cardDto.getCVVCode_3());
            card.setParole4(cardDto.getParole_4());
            card.setValidityPeriod(cardDto.getValidityPeriod());

            // card.setRole(roleRepository.findByRoleName(RoleName.CUSTOMER));

            if (cardDto.getCardType()==1){
                  card.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.UZCARD));
            }else if (cardDto.getCardType()==2){
                  card.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.HUMO));
            }else if (cardDto.getCardType()==3){
                  card.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.VISA));
            }

            card.setEnabled(Period.between(card.getValidityPeriod(), LocalDate.now()).getDays() >= 0);
            cardRepository.save(card);
            return new Result("Card added",true);
      }


      public Result getCardById(Integer id){
            Optional<BankCard> optionalCard = cardRepository.findById(id);
            return optionalCard.map(card -> new Result("Card ",
                    true, card)).orElseGet(() -> new Result("Card not found", false));
      /* if (!optionalCard.isPresent())
                  return new Result("Card not found",false);
            return new Result("Card ",true,optionalCard.get());*/
      }


      public Result editCardById(BankCardDto cardDto, Integer id){

            Optional<Banks> byId = bankRepository.findById(cardDto.getBankId());
            if (!byId.isPresent())
            return new Result("Bank not found",false);

             Optional<BankCard> optionalCard = cardRepository.findById(id);
            if (!optionalCard.isPresent())
            return new Result("Card not found",false);


            boolean exists = cardRepository.existsByCVVCode3AndIdNot(cardDto.getCVVCode_3(), id);
            if (exists)
                  return new Result("Such CVVCode already exist",false);

            boolean existsByParole4 = cardRepository.existsByParole4AndIdNot(cardDto.getParole_4(),id);
            if (existsByParole4)
                  return new Result("Such Parole already exist",false);

            boolean existsByNumber16 = cardRepository.existsByNumber16AndIdNot(cardDto.getNumber_16(),id);
            if (existsByNumber16)
                  return new Result("Such Number already exist",false);


             BankCard editCard = optionalCard.get();
            editCard.setFirstnameCustomer(cardDto.getFirstname());
            editCard.setLastnameCustomer(cardDto.getLastname());
            editCard.setBalanceInSum(cardDto.getBalanceInSum());
            editCard.setBalanceInDollar(cardDto.getBalanceInDollar());
            editCard.setNumber16(cardDto.getNumber_16());
            editCard.setValidityPeriod(cardDto.getValidityPeriod());
            editCard.setCVVCode3(cardDto.getCVVCode_3());
            editCard.setParole4(cardDto.getParole_4());
            editCard.setBankName(byId.get());

            if (cardDto.getCardType()==1){
                  editCard.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.UZCARD));
            }else if (cardDto.getCardType()==2){
                  editCard.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.HUMO));
            }else if (cardDto.getCardType()==3){
                  editCard.setCardType(cardTypeRepository.findByCardTypeName(CardTypeName.VISA));
            }

            editCard.setEnabled(Period.between(LocalDate.now(), editCard.getValidityPeriod()).getDays() >= 0);
            cardRepository.save(editCard);
            return new Result("Card added",true);
      }


      public Result deleteCard(Integer id){
            try {
                  cardRepository.deleteById(id);
                  return new Result("Card deleted",true);
            }catch (Exception e){
                  return new Result("Card not deleted",false);
            }
      }

}

