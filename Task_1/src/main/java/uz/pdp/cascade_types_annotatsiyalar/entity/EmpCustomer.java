package uz.pdp.cascade_types_annotatsiyalar.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.cascade_types_annotatsiyalar.entity.enumClass.Role;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmpCustomer implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String  email;

    // @Column(nullable = false)
    private String password;


    // CUSTOMER QÖSHGANDA
    private String kindOffCustomer;

    // F_DIRECTOR,M_OF_FILIAL,F_EMPLOYEE larni  QÖSHGANDA KERAK
    @JsonIgnore // REKUSIYANI OLDINI OLISH U-N
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Filial filial;        // FILIAL MANAGER UCHUN KÖP FILIAL BÖLADI

   // filial ichidagi EmpCustomer u-n SHU  EmpCustomer ni beramiz
    public void setFilial(Filial filial) {

     for (EmpCustomer filialEmployee : filial.getFilialEmployees()) {
         filialEmployee.setId(this.id);
     }
     Set<EmpCustomer> filialInternManagers = filial.getFilialInternManagers();
     filialInternManagers.add(this);

     filial.setFilialDirector(this);
     filial.setFilialManager(this);
     this.filial = filial;
    }



    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled;

    private String  emailCode;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}




