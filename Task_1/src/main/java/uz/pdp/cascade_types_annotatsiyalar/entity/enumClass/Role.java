package uz.pdp.cascade_types_annotatsiyalar.entity.enumClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.RoleName;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements GrantedAuthority {

    // User lar soni chegaralanmagan, Role lar soni chegaralangan böladi shu sababli Integer qölladik
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ENUM/NI CHAQIRAMIZ.Enum lar DB ga yozilsa, raqam bölib qoladi. shu sababli EnumType.STRING ga ögiramiz
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();  // String qaytaradi
    }

}
