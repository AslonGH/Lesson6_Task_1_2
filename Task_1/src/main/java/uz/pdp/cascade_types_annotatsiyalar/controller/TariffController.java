package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.TariffDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;
import uz.pdp.cascade_types_annotatsiyalar.service.TariffService;

import java.util.UUID;

@RestController
@RequestMapping("/api/tariff")
public class TariffController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    TariffService tariffService;



    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @PostMapping(value = "/add/tariff")
    public HttpEntity<?> addTariff(@RequestBody TariffDto tariffDto) {
        ApiResponse apiResponse = tariffService.addTariff(tariffDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @GetMapping(value = "/get/tariff")
    public HttpEntity<?> getTariff() {
        ApiResponse apiResponse = tariffService.getTariff();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editTariff(@PathVariable UUID id,@RequestBody TariffDto tariffDto) {
        ApiResponse apiResponse = tariffService.editTariff(id,tariffDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteTariff(@PathVariable UUID id) {
        ApiResponse apiResponse = tariffService.deleteTariff(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}






