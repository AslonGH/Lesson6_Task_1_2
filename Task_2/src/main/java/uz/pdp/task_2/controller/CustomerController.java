

package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.LoginDtoo;
import uz.pdp.task_2.payload.RegisterDtoo;
import uz.pdp.task_2.security.JwtProvider;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.CustomerService;


@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "/register/customer")
    public HttpEntity<?>registerUser(@RequestBody RegisterDtoo registerDto){
        ApiResponse apiResponse = customerService.registerCustomer(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @GetMapping( value = "/verifyEmail/customer")
    public HttpEntity<?>verifyEmail(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse=customerService.verifyEmail(emailCode,email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping(value = "/login/customer")
    public HttpEntity<?>login(@RequestBody LoginDtoo loginDto){
        ApiResponse apiResponse=customerService.loginCustomer(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    /*@PostMapping("/login")
    public HttpEntity<?>loginToSystem(@RequestBody LoginDto loginDto){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            Employee employee = (Employee) authenticate.getPrincipal();
     String token = JwtProvider.generateToken(loginDto.getUsername(), employee.getRoles());

     return ResponseEntity.ok(token);


    }catch (BadCredentialsException exception){

     return ResponseEntity.status(401).body("Login yoki parol xato");
    }
    }*/


}


