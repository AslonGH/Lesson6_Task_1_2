package uz.pdp.cascade_types_annotatsiyalar.payload;
import lombok.Data;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyDto {

    private String      name;
    private UUID        companyDirectorID;
}
