package uz.pdp.cascade_types_annotatsiyalar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contact_company")
@EntityListeners(AuditingEntityListener.class)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Integer   id;    // ID NI INTEGER QILGANDAN KEYIN DB GA QÖSHILDI

    private   String    name;

    @OneToOne
    private   EmpCustomer  companyDirector;

}