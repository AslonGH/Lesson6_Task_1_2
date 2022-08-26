package uz.pdp.task_2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDtoo {

    @NotNull
    private String username;

    @NotNull
    private String password;


}
