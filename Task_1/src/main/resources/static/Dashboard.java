
/*
package uz.pdp.cascade_types_annotatsiyalar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dashboard {

    @Id
    @GeneratedValue
    private UUID id;

    // BOSH DITACTOR KÖRADI
    private Double dailyIncome;
    private Double monthlyIncome;
    private Double halfQuarterIncome;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SimCard>simCarts;      // Sotilgan sim kartalar

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tariff>tariffs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Package>packages;





    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


}
*/
