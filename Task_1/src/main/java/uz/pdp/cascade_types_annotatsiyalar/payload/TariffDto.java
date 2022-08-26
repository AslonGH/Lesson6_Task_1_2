package uz.pdp.cascade_types_annotatsiyalar.payload;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class TariffDto {

    private String  name;
    private Integer deadLine;
    private String  expireDate;

    private Double  mb;
    private Integer sms;
    private Double  minuteBetweenInternSet;
    private Double  minuteBetweenExternSet;

    private Boolean isForJusticePerson;
    private Double  transitionPrice;
    private Double  price;

     private UUID simCardID;
}
    //private Set<UUID> simCards;

    /*
    private String  tariffLastActiveDay;      // TARIFNI SIMCARTA SOTIB OLSA QÖYILADI,yoki put qilishda
    private double priceForMb;
    private double priceForSms;
    private double priceForMinuteBetweenInternSet;
    private double priceForMinuteBetweenExternSet;*/


