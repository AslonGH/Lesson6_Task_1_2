package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.SimCardDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.FilialService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

import java.util.UUID;

@RestController
@RequestMapping("/api/simCard")
public class SimCardController {

    @Autowired
    FilialService filialService;

    @Autowired
    SimCardService simCardService;

    @Transactional(rollbackFor = NullPointerException.class)
    @PreAuthorize(value ="hasAnyRole('NUMBER_MANAGER','FILIAL_EMPLOYEE')")
    @PostMapping(value = "/add")
    public HttpEntity<?> addSimCard(@RequestBody SimCardDto simCardDto) {
        ApiResponse apiResponse = simCardService.addSimCard(simCardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('NUMBER_MANAGER','FILIAL_DIRECTOR')")
    @GetMapping(value = "/get")
    public HttpEntity<?> getSimCard() {
        ApiResponse apiResponse = simCardService.getSimCards();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @Transactional(rollbackFor = NullPointerException.class)
    @PreAuthorize(value ="hasAnyRole('NUMBER_MANAGER','FILIAL_EMPLOYEE')")
    @PutMapping(value = "/edit/{id}")
    public HttpEntity<?> editSimCard(@PathVariable UUID id,@RequestBody SimCardDto simCardDto) {
        ApiResponse apiResponse = simCardService.editSimCard(id,simCardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('NUMBER_MANAGER','FILIAL_EMPLOYEE')")
    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> deleteSimCard(@PathVariable UUID id) {
        ApiResponse apiResponse = simCardService.deleteSimCard(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}











