package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.RegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.UserRegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.FilialDirectorService;


@RestController
@RequestMapping("/api/auth")
public class FilialDirectorController {


     @Autowired
     FilialDirectorService filialDirectorService;


     @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
     @PostMapping("/register/filialDirector")
     public HttpEntity<?>registerDirector( @RequestBody UserRegisterDto filialDirectorDto){
        ApiResponse apiResponse = filialDirectorService.registerDirector(filialDirectorDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
     }

     @PostMapping("/login/filialDirector")
     public HttpEntity<?>loginDirector(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=filialDirectorService.loginDirector(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
     }


     // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
     @GetMapping("/verifyEmail/filialDirector")
     public HttpEntity<?>employeeVerifyEmail(@RequestParam String emailCode, @RequestParam String email){
         ApiResponse apiResponse=filialDirectorService.directorVerifyEmail(emailCode,email);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
     }

}










