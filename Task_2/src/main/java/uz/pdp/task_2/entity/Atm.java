package uz.pdp.task_2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.pdp.task_2.entity.enumClass.CardType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)  // KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private  Double   maxWithdrawLimitDollar;
    private  Double   maxWithdrawLimitSum;

    private  Double   minRestMoneyInSum;
    private  Double   minRestMoneyInDollar;

    private  Double   currentBalanceInSum;
    private  Double   currentBalanceInDollar;


    private  Double   commissionDepositOwnCardInPercent;
    private  Double   commissionDepositForeignCardInPercent;

    private  Double   commissionWithdrawOwnCardInPercent;
    private  Double   commissionWithdrawForeignCardInPercent;

    private  Double   budgetFromCommissionSum;
    private  Double   budgetFromCommissionDollar;


    @ManyToOne(cascade = CascadeType.ALL)
    private Address   address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Banks bank;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CardType> cardTypesFor;



    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
