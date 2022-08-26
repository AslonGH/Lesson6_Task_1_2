package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.PaymentsDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.PaymentService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    PaymentService paymentService;



    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PostMapping(value = "/add/Payment")
    public HttpEntity<?> addPayment(@RequestBody PaymentsDto paymentsDto) {
        ApiResponse apiResponse = paymentService.addPayment(paymentsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editPackage(@PathVariable UUID id,@RequestBody PaymentsDto paymentsDto) {
        ApiResponse apiResponse = paymentService.editPayment(id,paymentsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @GetMapping(value = "/get/Payment")
    public HttpEntity<?> getPayment() {
        ApiResponse apiResponse = paymentService.getPayment();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}






