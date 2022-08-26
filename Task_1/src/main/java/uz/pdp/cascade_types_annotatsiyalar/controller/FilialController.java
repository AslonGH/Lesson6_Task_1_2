package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.FilialDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.FilialService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FilialController {

    @Autowired
    FilialService filialService;


    @PreAuthorize(value = "hasRole('FILIAL_MANAGER')")
    @PostMapping(value = "/add/filial")
    public HttpEntity<?> addFilial(@RequestBody FilialDto filialDto) {
        ApiResponse apiResponse = filialService.addFilial(filialDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('FILIAL_MANAGER')")
    @PutMapping(value = "/edit/filial/{id}")
    public HttpEntity<?> editFilial(@PathVariable UUID id, @RequestBody FilialDto filialDto) {
        ApiResponse apiResponse = filialService.editFilial(id, filialDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasRole('FILIAL_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteFilial(@PathVariable UUID id) {
        ApiResponse apiResponse = filialService.deleteFilial(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}









