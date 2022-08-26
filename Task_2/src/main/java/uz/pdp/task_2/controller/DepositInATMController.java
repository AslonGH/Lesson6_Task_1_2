package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.entity.DepositInATM;
import uz.pdp.task_2.payload.DepositATMDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.DepositInATMService;

import java.util.List;

@RestController
@RequestMapping("/api/depositATM")
public class DepositInATMController {

    @Autowired
    DepositInATMService incomeService;


    @Transactional
    @PreAuthorize(value = "hasRole('BANK_EMPLOYEE')")
    @PostMapping
    public HttpEntity<?>addIncome(@RequestBody DepositATMDto incomeDto){
        Result result = incomeService.addIncome(incomeDto);
        return ResponseEntity.status(201).body(result);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getIncomes(){
        List<DepositInATM> incomeList = incomeService.getIncomes();
        return ResponseEntity.ok(incomeList);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/allByAtmId/{id}")
    public HttpEntity<?>getAllFillMoneyByATMId(@PathVariable Integer id){
        List<DepositInATM> incomeById = incomeService.getAllFillMoneyByATMId(id);
        return  ResponseEntity.status(incomeById !=null? HttpStatus.OK:HttpStatus.CONFLICT).body(incomeById);
    }



    @Transactional
    @PreAuthorize(value = "hasRole('BANK_EMPLOYEE')")
    @PutMapping("/{id}")
    public HttpEntity<?>editIncomes(@RequestBody DepositATMDto atmDto, @PathVariable Integer id){
        Result result = incomeService.editIncomeById(atmDto, id);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize(value = "hasRole('BANK_EMPLOYEE')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteIncome(@PathVariable Integer id){
        Result result = incomeService.deleteIncome(id);
        return ResponseEntity.ok(result);
    }


}
