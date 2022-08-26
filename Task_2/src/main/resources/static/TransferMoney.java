
/*

          Optional<Card> optionalFromCard = cardRepository.findById(depositDto.getFromCardId());
          if (!optionalFromCard.isPresent())
              return new Result("FromCardId not found",false);

          Optional<Card> optionalCard = cardRepository.findById(depositDto.getCard());
          if (!optionalCard.isPresent())
              return new Result("Card not found",false);

          Optional<Card> optionalToCard = cardRepository.findById(depositDto.getToCardId());
          if (!optionalToCard.isPresent())
              return new Result("toCardId not found",false);



          DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
            try {
                  Date date = dateFormat.parse(incomeDto.getDate());
                  java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                  income.setDate(sqlDate);
            } catch (Exception e) {
                return new Result("enter  date(dd.MM.yyyy)",false);
            }



            Card cardFrom = optionalFromCard.get();
            //  income.setFromCard(cardFrom);
            //  income.setToCard(optionalToCard.get());

            Card toCard = optionalToCard.get();
            // double balanceToCard = toCard.getBalance();
            // toCard.setBalance(balanceToCard+incomeDto.getAmount());

            cardRepository.save(toCard);
            return new Result("Transfer accomplished",true);

        public DepositInCard getIncomeById(Integer id) {
              Optional<DepositInATM> optionalIncome = depositATMRepository.findById(id);
              return optionalIncome.orElse(null);
        }


     cardFrom.setBalance(cardFrom.getBalance()-(incomeDto.getAmount()));
     cardRepository.save(cardFrom);

*/

/*
package uz.pdp.task_2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransferMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  UUID id;

    private  int   currency;       // 1 va 2 söm dollar

    private  int   amount;

    @OneToOne
    private BankATM bankomat;
    
    @ManyToOne
    private  Card     fromCard;

    @ManyToOne
    private  Card     toCard;


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
*/
