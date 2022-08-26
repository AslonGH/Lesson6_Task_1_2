package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.Data;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;

import java.util.List;
import java.util.UUID;

@Data
public class FilialDto {


    private String     name;

    private String     city;

    private UUID       filialManagerId;

    private UUID       filialDirectorId;

    private List<UUID> filialEmployeeIds;

    private Integer  companyId;

}
