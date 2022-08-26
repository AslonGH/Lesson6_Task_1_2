package uz.pdp.task_2.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDtoo {


    @NotNull
    @Size(min = 3,max = 50)
    private String   firstname;

    @NotNull
    @Length(min = 3,max = 50)
    private String   lastname;

    @NotNull
    private String   email;


    private String   password;


}
