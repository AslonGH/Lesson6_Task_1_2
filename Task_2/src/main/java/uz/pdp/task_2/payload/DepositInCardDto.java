package uz.pdp.task_2.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class DepositInCardDto {

    private  Integer   currency;       // 1:söm va 2:dollar
    private  Integer   bankNote;
    private  Integer   amount;
    private  Integer      bankomatID;
    private  Integer      cardId;
    private  String    date;

}
