package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.entity.Atm;
import uz.pdp.task_2.payload.BankATMDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.BankATMService;

import java.util.List;


@RestController
@RequestMapping("/bankATM")
public class BankATMController {

    @Autowired
    BankATMService bankomatService;


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addBankomat(@RequestBody BankATMDto bankomatDto) {
        Result result = bankomatService.addBankomat(bankomatDto);
        return ResponseEntity.status(201).body(result);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getBankomatById(@PathVariable Integer id) {
        Result cardById = bankomatService.getBankomatById(id);
        return ResponseEntity.status(cardById != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(cardById);
    }


     @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
     @GetMapping("/budgetById/{id}")
     public HttpEntity<?> getBankATMBudgetById(@PathVariable Integer id) {
         ApiResponse cardById = bankomatService.getBankATMBudgetById(id);
         return ResponseEntity.status(cardById != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(cardById);
     }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getBankATM() {
        List<Atm> bankATMS = bankomatService.getBankATMs();
        return ResponseEntity.ok(bankATMS);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editBankomat(@RequestBody BankATMDto bankomatDto, @PathVariable Integer id) {
        Result result = bankomatService.editBankomatById(bankomatDto, id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBankomat(@PathVariable Integer id) {
        Result result = bankomatService.deleteBankomat(id);
        return ResponseEntity.ok(result);
    }

}



