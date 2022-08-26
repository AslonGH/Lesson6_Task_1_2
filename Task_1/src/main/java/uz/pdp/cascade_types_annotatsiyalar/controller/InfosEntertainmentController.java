package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.InfosEntertainmentDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.EntertainmentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/infos")
public class InfosEntertainmentController {

    @Autowired
    EntertainmentService infEntService;


    @PreAuthorize(value = "hasAnyRole('TARIFF_MANAGER')")
    @PostMapping(value = "/add/InfEntertainment")
    public HttpEntity<?> addInfEntertainment(@RequestBody InfosEntertainmentDto infDto) {
        ApiResponse apiResponse = infEntService.addEntertainment(infDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('TARIFF_MANAGER')")
    @GetMapping(value = "/get/InfEntertainment")
    public HttpEntity<?> getInfEntertainment() {
        ApiResponse apiResponse = infEntService.getEntertainmentPopular();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editInfEntertainment(@PathVariable UUID id, @RequestBody InfosEntertainmentDto infDto) {
        ApiResponse apiResponse = infEntService.editEntertainment(id, infDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('TARIFF_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteInfEntertainment(@PathVariable UUID id) {
        ApiResponse apiResponse = infEntService.deleteEntertainment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}








