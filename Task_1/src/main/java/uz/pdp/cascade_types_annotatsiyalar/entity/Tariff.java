package uz.pdp.cascade_types_annotatsiyalar.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tariff {

    @Id
    @GeneratedValue
    private UUID       id;

    private String     name;

    private LocalDate  expireDate; // AMAL QILISH MUDDATI
    private Integer    deadLine;   // AMAL QILISH MUDDATI KUN

    private Boolean    isForJusticePerson;
    private Double     transitionPrice; // Ötish narxi
    private Double     price;           // TARIFNING ÖZINING NARXI
    private Boolean    isActive;                  // TARIFNI SIMCARTA SOTIB OLSA QÖYILADI,yoki put qilishda


    // SHU TARIF REJA BÖYICHA BERILGANLAR
    private Double  mb;
    private Integer sms;
    private Double  minuteBetweenInternSet;
    private Double  minuteBetweenExternSet;


    // price pastdagi uchchalasini yigindisiga teng.
    private Double  priceForMb;  // 50%
    private Double  priceForSms; // 5%
    private Double  priceForMinuteBetweenInternSet; // 5%
    private Double  priceForMinuteBetweenExternSet; // 30%


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}



