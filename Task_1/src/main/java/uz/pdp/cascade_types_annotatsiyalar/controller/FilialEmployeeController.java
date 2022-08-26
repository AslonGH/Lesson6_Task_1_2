package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.UserRegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.RegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.FilialEmployeeService;

import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class FilialEmployeeController {

    @Autowired
    FilialEmployeeService filialEmployeeService;


    @PreAuthorize(value ="hasAnyRole('MANAGER_OF_ONE_FILIAL','FILIAL_DIRECTOR')")
    @PostMapping(value = "/register/filialEmployee")
    public HttpEntity<?> addFilialEmployee(@RequestBody UserRegisterDto registerDto) {
        ApiResponse apiResponse = filialEmployeeService.registerEmployee(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping(value = "/login/employee")
    public HttpEntity<?> loginManager(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = filialEmployeeService.loginEmployee(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @GetMapping(value = "/verifyEmail/filialEmployee")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = filialEmployeeService.employeeVerifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasRole('FILIAL_EMPLOYEE')")
    @GetMapping(value = "/get/filialEmployee/{id}")
    public HttpEntity<?> getFilialEmployeeById(@PathVariable UUID id) {
       ApiResponse apiResponse = filialEmployeeService.getFilialEmployeeById(id);
       return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('MANAGER_OF_ONE_FILIAL','FILIAL_DIRECTOR','FILIAL_EMPLOYEE')")
    @PutMapping(value = "/edit/filialEmployee/{id}")
    public HttpEntity<?> editFilialEmployee(@PathVariable UUID id, @RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = filialEmployeeService.editFilialEmployee(id,registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('MANAGER_OF_FILIAL','FILIAL_DIRECTOR')")
    @DeleteMapping(value = "/delete/filialEmployee/{id}")
    public HttpEntity<?> deleteFilialEmployee(@PathVariable UUID id) {
      ApiResponse apiResponse = filialEmployeeService.deleteFilialEmployee(id);
      return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}






