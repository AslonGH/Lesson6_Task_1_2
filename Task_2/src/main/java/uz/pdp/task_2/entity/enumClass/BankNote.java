package uz.pdp.task_2.entity.enumClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.task_2.entity.enums.BankNoteName;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankNote implements GrantedAuthority {

    // Userlar soni chegaralanmagan, Role lar soni chegaralangan böladi shu sababli Integer qölladik
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //ENUM/NI CHAQIRAMIZ.Enum lar DB ga yozilsa, raqam bölib qoladi.shu sababli
    @Enumerated(EnumType.STRING)
    private BankNoteName bankNoteName;


    @Override
    public String getAuthority() {
        return bankNoteName.name();
    }
}
