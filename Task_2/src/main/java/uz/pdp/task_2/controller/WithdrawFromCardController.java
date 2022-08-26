package uz.pdp.task_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.entity.WithdrawFromCard;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.payload.WithdrawFromCardDto;
import uz.pdp.task_2.service.ApiResponse;
import uz.pdp.task_2.service.WithdrawFromCardService;

import java.util.List;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawFromCardController {

    @Autowired
    WithdrawFromCardService outComeService;

    @Transactional
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PostMapping
    public HttpEntity<?>addWithdrawMoney(@RequestBody WithdrawFromCardDto outcomeDto){
       Result result = outComeService.addWithdrawMoney(outcomeDto);
       return ResponseEntity.status(result !=null ? HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getWithdrawMoney(){
        List<WithdrawFromCard> outcomeList = outComeService.getWithdrawMoney();
        return ResponseEntity.ok(outcomeList);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/dailyWithdraw/{id}/{day}")
    public HttpEntity<?>getDailyWithdrawFromCardByATMId(@PathVariable Integer id, @PathVariable String day){
        ApiResponse dailyWithdrawFromCardByATMId = outComeService.getDailyWithdrawFromCardByATMId(id, day);
        return  ResponseEntity.status(dailyWithdrawFromCardByATMId.isSuccess() ? HttpStatus.OK:HttpStatus.CONFLICT).
                body(dailyWithdrawFromCardByATMId);
    }



    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?>getWithdrawMoneyById(@PathVariable Integer id){
        WithdrawFromCard outcomeById = outComeService.getWithdrawMoneyById(id);
        return  ResponseEntity.status(outcomeById !=null?HttpStatus.OK:HttpStatus.CONFLICT).body(outcomeById);
    }

    @Transactional
    @PutMapping("/{id}")
    public HttpEntity<?>editWithdrawMoney(@RequestBody WithdrawFromCardDto outcomeDto, @PathVariable Integer id){
        Result result = outComeService.editWithdrawMoneyById(outcomeDto, id);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteWithdrawMoney(@PathVariable Integer id){
        Result result = outComeService.deleteWithdrawMoney(id);
        return ResponseEntity.ok(result);
    }

}
