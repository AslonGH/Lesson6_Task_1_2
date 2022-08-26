package uz.pdp.task_2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.AddrossDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.AddressService;


@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addAddress(@RequestBody AddrossDto addressDto) {
        Result result = addressService.addAddress(addressDto);
        return ResponseEntity.status(201).body(result);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editAddress(@RequestBody AddrossDto addressDto, @PathVariable Integer id) {
        Result result = addressService.editAddressById(addressDto, id);
        return ResponseEntity.ok(result);
    }

}



