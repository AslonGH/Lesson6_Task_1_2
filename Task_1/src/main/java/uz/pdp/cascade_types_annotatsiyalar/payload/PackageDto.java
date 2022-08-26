package uz.pdp.cascade_types_annotatsiyalar.payload;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class PackageDto {

    private double      price;

    private double      amount;

    private Boolean     isPackageSold;           // Sotilgandan keyin qöyiladi
    private LocalDate   dayOfPackageSold;

    private Integer     typeOfPackage;           // SMS MB YOKI DAQIQA

    private Integer     validityDays;

    private Boolean     addToRestOffPackage;

    private UUID        tariffID;
    private UUID        simCardID;

    // private List<UUID>  simCardIDs;         // Sim Card ga Package sotib olsak qöshamiz
}
