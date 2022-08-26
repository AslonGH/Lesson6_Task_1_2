package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
public class CustomerDto {


    @NotNull
    @Size(min = 3,max = 50)
    private String firstname;

    @NotNull
    @Length(min = 3,max = 50)
    private String    lastname;

    @NotNull
    @Email
    private String    email;

    private String    password;

    private Integer   kindOffCustomer;

   // private Set<UUID> simCardsIDs;
}
