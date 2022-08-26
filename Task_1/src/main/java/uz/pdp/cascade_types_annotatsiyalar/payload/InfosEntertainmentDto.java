package uz.pdp.cascade_types_annotatsiyalar.payload;
import lombok.Data;

@Data
public class InfosEntertainmentDto {

    private String   name;

    private Integer  category;              // MB;SMS;VOICE Category 1,2,3

    private Integer  typeOfPeriod;           // 1 va 2

    private Double   priceProTypeOfPeriod;

    private Integer  deadline;              // QANCHA MUDDATGA SOTIB OLINDI

}
