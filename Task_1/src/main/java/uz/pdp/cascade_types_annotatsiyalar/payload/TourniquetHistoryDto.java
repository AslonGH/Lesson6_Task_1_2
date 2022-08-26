package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TourniquetHistoryDto {

    private  UUID    cardId;

    private  String  getInTime;     // "dd.MM.yyyy hh:mm:ss"

    private  String  getOutTime;

}
