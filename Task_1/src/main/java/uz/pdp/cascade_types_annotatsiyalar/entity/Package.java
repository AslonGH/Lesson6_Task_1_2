package uz.pdp.cascade_types_annotatsiyalar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class  Package {

    @Id
    @GeneratedValue
    private UUID         id;

    private Double       price;

    private Double       amount;

    private  String      typeOfPackage;        // SMS MB YOKI DAQIQA

    private  Integer     validityDays;

    private  Boolean     addToRestOffPackage;

    @ManyToOne
    private   Tariff     tariff;               // Qaysi tarifdagi  SimCard lar olishi mumkin

    private   Boolean    isPackageSold;        // Sotilgandan keyin qöyiladi

    private   LocalDate  dayOfPackageSold;     // Sotilgandan keyin qöyiladi

    @ManyToOne
    private  SimCard   simCard;                // Faqat SimCard ga tegishli package Tariff ga aloqasi yöq bölsa



    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}