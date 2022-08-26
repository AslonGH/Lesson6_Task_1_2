package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TourniquetCardDto {


    private  String     expireDate;

    private  UUID       employeeId;

    private  Integer    companyId;

}
