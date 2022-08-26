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
import java.util.Collections;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class InfosEntertainment {


    @Id
    @GeneratedValue
    private UUID     id;

    private String   name;                  // OB HAVO MALUMOTI(SMS), TARZDA KELADI,GOODoK;...
    private String   category;              // MB;SMS;VOICE Category
    private String   typeOfPeriod;          // OYLIK YOKI KUNLIK
    private Double   priceProTypeOfPeriod;
    private Integer  deadline;              // QANCHA MUDDATGA SOTIB OLINDI
    private Boolean  isActive;              // SOTIB OLINSA true qilamiz


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SimCard simCard;


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
