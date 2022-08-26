package uz.pdp.cascade_types_annotatsiyalar.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
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
public class SimCard {

    @Id
    @GeneratedValue
    private UUID  id;

    private  final String  uzbCode="+998";
    private  Integer       companyCode;        // 93 yoki 94 kabi

    @Length(max = 13)
    private  String     number;
    private  Double     balance;
    private  Boolean    canBeInMinusBalance;
    private  LocalDate  tariffLastActiveDay;

    private  Boolean   enabled;                // YAROQLI, RÖYXATDAN ÖTGAN,SOTILGAN

    // optional = false bölgani uchun,customer ga qiymat berish shart, EmpCustomer ichidagi  hali save bölmagan
    // simCard ni söraydi shunda shuni beramiz.customer ichida SimCard bor, rekusiya bölmasligi u-n  @JsonIgnore qölllaymiz
    @JsonIgnore
    @OneToOne(optional = false, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private EmpCustomer customer;


    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private  Tariff  tariff;


    @OneToMany(mappedBy = "simCard", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Set<InfosEntertainment> entertainments;
    public void setEntertainments(Set<InfosEntertainment> entertainments) {
        for (InfosEntertainment entertainment : entertainments) {
            entertainment.setSimCard(this);
        }
        this.entertainments = entertainments;
    }


    @OneToMany(mappedBy = "simCard",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Package> packages;
    public void setPackages(Set<Package> packages) {
        for (Package aPackage : packages) {
            aPackage.setSimCard(this);
        }
        this.packages = packages;
    }


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}







