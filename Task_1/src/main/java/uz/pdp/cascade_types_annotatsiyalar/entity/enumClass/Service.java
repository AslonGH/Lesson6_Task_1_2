package uz.pdp.cascade_types_annotatsiyalar.entity.enumClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.ServiceName;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Service implements GrantedAuthority {

    // User lar soni chegaralanmagan, Role lar soni chegaralangan böladi shu sababli Integer qölladik
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ENUM/NI CHAQIRAMIZ.Enum lar DB ga yozilsa, raqam bölib qoladi. shu sababli
    @Enumerated(EnumType.STRING)
    private ServiceName serviceName;

    @Override
    public String getAuthority() {
        return serviceName.name();  // String qaytaradi
    }

}
