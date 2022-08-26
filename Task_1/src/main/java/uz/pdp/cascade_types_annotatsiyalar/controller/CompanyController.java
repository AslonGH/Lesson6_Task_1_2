package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.CompanyDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.CompanyService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CompanyController {


    @Autowired
    CompanyService companyService;


    @PreAuthorize(value = "hasAnyRole('COMPANY_DIRECTOR')")
    @PostMapping(value = "/add/company")
    public HttpEntity<?> addCompany(@RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('COMPANY_DIRECTOR')")
    @PutMapping(value = "/edit/company/{id}")
    public HttpEntity<?> editCompany(@PathVariable Integer id, @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('COMPANY_DIRECTOR')")
    @DeleteMapping(value = "delete/company/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}












