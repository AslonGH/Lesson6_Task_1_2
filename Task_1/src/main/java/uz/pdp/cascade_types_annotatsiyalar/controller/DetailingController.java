package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.DetailingDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.DetailingService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class DetailingController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    DetailingService detailingService;



    @PreAuthorize(value ="hasRole('CUSTOMER')")
    @PostMapping(value = "/add/detailing")
    public HttpEntity<?> addDetailing(@RequestBody DetailingDto detailingDto) {
        ApiResponse apiResponse = detailingService.addDetailing(detailingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PutMapping(value = "/edit/detailing/{id}")
    public HttpEntity<?> editDetailing(@PathVariable UUID id, @RequestBody DetailingDto detailingDto) {
        ApiResponse apiResponse = detailingService.editDetailing(id, detailingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    // MIJOZ ID SI ORQALI SHU MIJOZNING QILGAN HARAKATLARI
    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @GetMapping(value = "/get/detailing/{id}/{date}/{date1}")
    public HttpEntity<?> getDetailing(@PathVariable UUID id, @PathVariable String date, @PathVariable String date1) {
        ApiResponse apiResponse = detailingService.getDetailing(id,date,date1);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}






