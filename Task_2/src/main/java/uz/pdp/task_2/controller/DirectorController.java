package uz.pdp.task_2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.LoginDtoo;
import uz.pdp.task_2.payload.RegisterDtoo;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.DirectorService;


@RestController
@RequestMapping("/api/auth")
public class DirectorController {

    @Autowired
    DirectorService directorService;



    @PostMapping(value = "/register/director")
    public HttpEntity<?>registerDirector(@RequestBody RegisterDtoo registerDto){
        ApiResponse apiResponse = directorService.registerDirector(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @GetMapping( value = "/verifyEmail/director")
    public HttpEntity<?>verifyEmail(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse=directorService.verifyEmail(emailCode,email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping(value = "/login/director")
    public HttpEntity<?>loginDirector(@RequestBody LoginDtoo loginDto){
        ApiResponse apiResponse=directorService.loginDirector(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }




/*
    // BU CARTA EKGALARI BÖLSIN , token kerakmas
    @PostMapping("/login")
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
    }
*/

}


