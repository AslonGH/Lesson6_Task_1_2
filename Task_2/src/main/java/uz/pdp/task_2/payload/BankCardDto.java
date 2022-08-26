package uz.pdp.task_2.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BankCardDto {

  private String    firstname;
  private String    lastname;
  private Double    balanceInSum;
  private Double    balanceInDollar;
  private String    expiredDate;

  private String    number_16;
  private String    CVVCode_3;
  private String    parole_4;
  private LocalDate validityPeriod;
  private Integer   cardType;
  private Integer      bankId;

}
