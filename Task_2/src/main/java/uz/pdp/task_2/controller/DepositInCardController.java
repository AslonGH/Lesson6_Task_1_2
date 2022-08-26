package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.entity.DepositInCard;
import uz.pdp.task_2.payload.DepositInCardDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.DepositInCardService;

import java.util.List;

@RestController
@RequestMapping("/api/depositInCard")
public class DepositInCardController {

    @Autowired
    DepositInCardService depositInCardService;

    @Transactional
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PostMapping
    public HttpEntity<?>addIncome(@RequestBody DepositInCardDto incomeDto){
        Result result = depositInCardService.addDepositInCard(incomeDto);
        return ResponseEntity.status(201).body(result);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getIncomes(){
        List<DepositInCard> incomeList = depositInCardService.getDepositInCards();
        return ResponseEntity.ok(incomeList);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?>getIncomesById(@PathVariable Integer id){
         DepositInCard incomeById = depositInCardService.getDepositInCardById(id);
         return  ResponseEntity.status(incomeById !=null? HttpStatus.OK:HttpStatus.CONFLICT).body(incomeById);
     }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/allByAtmId/{id}")
    public HttpEntity<?>getIncomesByATMId(@PathVariable Integer id){
         List<DepositInCard> incomeById = depositInCardService.getDepositInCardByATMId(id);
         return  ResponseEntity.status(incomeById !=null? HttpStatus.OK:HttpStatus.CONFLICT).body(incomeById);
     }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/dailyIncomes/{id}/{day}")
    public HttpEntity<?>getDailyIncomesByATMId(@PathVariable Integer id, @PathVariable String day){
        ApiResponse dailyDepositInCardByATMId = depositInCardService.getDailyDepositInCardByATMId(id, day);
        return  ResponseEntity.status(dailyDepositInCardByATMId.isSuccess() ? HttpStatus.OK:HttpStatus.CONFLICT).
                body(dailyDepositInCardByATMId);
     }



    @Transactional
    @PutMapping("/edit/{id}")
    public HttpEntity<?>editIncomes(@RequestBody DepositInCardDto atmDto, @PathVariable Integer id){
        Result result = depositInCardService.editDepositInCardById(atmDto, id);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?>deleteIncome(@PathVariable Integer id){
        Result result = depositInCardService.deleteDepositInCard(id);
        return ResponseEntity.ok(result);
    }



}
