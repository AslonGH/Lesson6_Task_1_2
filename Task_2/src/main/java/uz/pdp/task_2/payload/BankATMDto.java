package uz.pdp.task_2.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BankATMDto {



        private  Double   maxWithdrawLimitDollar;
        private  Double   maxWithdrawLimitSum;

        private  Double   minRestMoneyInSum;
        private  Double   minRestMoneyInDollar;

        private  Double   currentBalanceInSum;
        private  Double   currentBalanceInDollar;

        private  Double   commissionWithdrawOwnCardInPercent;
        private  Double   commissionWithdrawForeignCardInPercent;

        private  Double   commissionDepositOwnCardInPercent;
        private  Double   commissionDepositForeignCardInPercent;

        private  Double    budgetFromCommissionSum;
        private  Double    budgetFromCommissionDollar;

        private   Integer         addressID;
        private   Integer      bankID;
        private  List <Integer> cardTypesFor;

}
