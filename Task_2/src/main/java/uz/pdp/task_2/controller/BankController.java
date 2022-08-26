package uz.pdp.task_2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.BankDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.BankService;


@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    BankService bankService;


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addBank(@RequestBody BankDto bankDto) {
        Result result = bankService.addBank(bankDto);
        return ResponseEntity.status(201).body(result);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editBank(@RequestBody BankDto bankDto, @PathVariable Integer id) {
        Result result = bankService.editBankById(bankDto, id);
        return ResponseEntity.ok(result);
    }

}



