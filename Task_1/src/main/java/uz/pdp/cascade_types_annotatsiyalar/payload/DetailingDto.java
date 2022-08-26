package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
public class DetailingDto {

    private  Integer   kindOfAction;
    private  UUID      simCardFromUUID;
    private  UUID      simCardToUUID;
    private  LocalDate localDate;

    // private UUID []   simCardsUUID;

    public DetailingDto(Integer kindOfAction, UUID simCardFromUUID, LocalDate localDate) {
        this.kindOfAction = kindOfAction;
        this.simCardFromUUID = simCardFromUUID;
        this.localDate = localDate;
    }
}
