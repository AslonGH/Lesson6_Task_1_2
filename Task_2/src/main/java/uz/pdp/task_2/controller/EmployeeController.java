


package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.LoginDtoo;
import uz.pdp.task_2.payload.RegisterDtoo;
import uz.pdp.task_2.repository.EmployeeRepository;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.EmployeeService;


@RestController
@RequestMapping("/api/auth")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PostMapping("/register/employee")
    public HttpEntity<?> registerEmployee(@RequestBody RegisterDtoo registerDto) {
        ApiResponse apiResponse = employeeService.registerEmployee(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping(value = "/login/employee")
    public HttpEntity<?> loginEmployee(@RequestBody LoginDtoo loginDto) {
        ApiResponse apiResponse = employeeService.loginEmployee(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

   // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping(value = "/verifyEmail/employee")
    public HttpEntity<?> employeeVerifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = employeeService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // XODIMLAR RÖYXATI KÖRINIB TURISHI KERAK.
    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/getEmployees")
    public HttpEntity<?> getEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

}



