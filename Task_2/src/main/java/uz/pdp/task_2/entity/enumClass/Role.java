
package uz.pdp.task_2.entity.enumClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.task_2.entity.enums.RoleName;


import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity     // BU CLASS TYPI String roleName() ni qaytaradi
public class Role implements GrantedAuthority {

    // Userlar soni chegaralanmagan, Role lar soni chegaralangan böladi shu sababli Integer qölladik
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //ENUM/NI CHAQIRAMIZ.Enum lar DB ga yozilsa, raqam bölib qoladi.shu sababli
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();      //  String qaytaradi
    }

}

