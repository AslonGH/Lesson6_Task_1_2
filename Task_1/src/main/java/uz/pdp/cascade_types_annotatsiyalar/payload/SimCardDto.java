package uz.pdp.cascade_types_annotatsiyalar.payload;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class SimCardDto {

    @Length(max = 7)
    private  String     number;
    private  Integer    companyCode;
    private  Double     balance;
    private  Boolean    canBeInMinusBalance;
    private  UUID       customerId;

    private  UUID         tariffId;
    private  List <UUID>  entertainmentIds;
    private  List <UUID>  packageIds;
}
