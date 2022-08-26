package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.CustomerDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.RegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.CustomerService;

@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @PreAuthorize(value ="hasAnyRole('MANAGER_OF_FILIAL','FILIAL_EMPLOYEE')")
    @PostMapping(value = "/register/customer")
    public HttpEntity<?> addCustomer(@RequestBody CustomerDto registerDto) {
        ApiResponse apiResponse = customerService.registerCustomer(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping(value = "/login/customer")
    public HttpEntity<?> loginCustomer(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = customerService.loginCustomer(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @GetMapping(value = "/verifyEmail/customer")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = customerService.managerVerifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}






