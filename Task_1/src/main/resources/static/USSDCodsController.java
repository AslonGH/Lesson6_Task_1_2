/*
package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.USSDCodsDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;


import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class USSDCodsController {





    @PreAuthorize(value ="hasAnyRole('TARIFF_MANAGER')")
    @PostMapping(value = "/USSDCodsService")
    public HttpEntity<?>serviceUSSDCodsService(@RequestBody USSDCodsDto ussdCodsDto)
    {
        ApiResponse apiResponse = ussdCodsService.serviceUSSDCod(ussdCodsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }



    @PreAuthorize(value ="hasAnyRole('TARIFF_MANAGER')")
    @PostMapping(value = "/add/USSDCods")
    public HttpEntity<?> addUSSDCods(@RequestBody USSDCodsDto ussdCodsDto) {
        ApiResponse apiResponse = ussdCodsService.addUSSDCods(ussdCodsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('TARIFF_MANAGER','CUSTOMER')")
    @GetMapping(value = "/get/USSDCods")
    public HttpEntity<?> getUSSDCods() {
        ApiResponse apiResponse = ussdCodsService.getUSSDCods();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editUSSDCods(@PathVariable UUID id,@RequestBody USSDCodsDto ussdCodsDto) {
        ApiResponse apiResponse = ussdCodsService.editUSSDCods(id,ussdCodsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }



//    @PreAuthorize(value ="hasRole('TARIFF_MANAGER')")
//    @DeleteMapping(value = "/{id}")
//    public HttpEntity<?> deleteUSSDCods(@PathVariable UUID id) {
//        ApiResponse apiResponse = ussdCodsService.deleteUSSDCods(id);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
//    }


}






*/
