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
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Filial {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    private String city;

    @ManyToOne(cascade = CascadeType.ALL)
    private  EmpCustomer filialManager;

    @OneToOne(cascade = CascadeType.ALL)
    private  EmpCustomer filialDirector;

    @OneToMany(mappedBy = "filial",cascade = CascadeType.ALL)
    private Set<EmpCustomer> filialInternManagers;   // Role MANAGER_OF_ONE_FILIAL  bölsa repostorydan olib kelamiz

   @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<EmpCustomer> filialEmployees;


    @ManyToOne
    private Company company;


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
