package uz.pdp.cascade_types_annotatsiyalar.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;


@Data
public class PaymentsDto {


    private Integer  typeOfPayment;

    private UUID customerId;

    private LocalDate localDate;

    public PaymentsDto(Integer typeOfPayment, UUID customerId, LocalDate localDate) {
        this.typeOfPayment = typeOfPayment;
        this.localDate = localDate;
        this.customerId = customerId;

    }
}