
package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.CompanyBudgetDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.CompanyBudgetService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class BudgetController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    CompanyBudgetService budgetService;


    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @GetMapping(value = "/get/budget")
    public HttpEntity<?> getBudget() {
        ApiResponse apiResponse = budgetService.getBudget();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editBudget(@PathVariable UUID id, @RequestBody CompanyBudgetDto budgetDto) {
        ApiResponse apiResponse = budgetService.editCompany(id,budgetDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteBudget(@PathVariable UUID id) {
        ApiResponse apiResponse = budgetService.deleteCompanyBudget(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }



}

