package uz.pdp.task_2.payload;
import lombok.Data;

import java.util.UUID;


@Data
public class WithdrawFromCardDto {


     private  Integer   currency;

     private  Integer   bankNote;

     private  Integer   amount;

     private  String   date;

     private  Integer      bankomatID;

     private  Integer      cardID;

}
