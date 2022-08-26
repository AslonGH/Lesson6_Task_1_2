package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.PackageDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.PackageService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

import java.util.UUID;

@RestController
@RequestMapping("/api/package")
public class PackageController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    PackageService packageService;



    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @PostMapping(value = "/add/package")
    public HttpEntity<?> addPackage(@RequestBody PackageDto packageDto) {
        ApiResponse apiResponse = packageService.addPackage(packageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @GetMapping(value = "/get/package")
    public HttpEntity<?> getPackage() {
        ApiResponse apiResponse = packageService.getPackages();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @PutMapping(value = "/edit/{id}")
    public HttpEntity<?> editPackage(@PathVariable UUID id,@RequestBody PackageDto packageDto) {
        ApiResponse apiResponse = packageService.editPackage(id,packageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> deletePackage(@PathVariable UUID id) {
        ApiResponse apiResponse = packageService.deletePackage(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


}






