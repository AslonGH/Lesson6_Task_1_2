package uz.pdp.cascade_types_annotatsiyalar.entity;

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
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyBudget {

    @Id
    @GeneratedValue
    private UUID  id;


    private  String      incomeType;

    private  Double      balance;

    private  LocalDate   localDate;


    @ManyToOne
    private Company  company;


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
