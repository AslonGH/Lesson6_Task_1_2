package uz.pdp.cascade_types_annotatsiyalar.payload;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;


@Data
public class LoginDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;


}
