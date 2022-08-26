package uz.pdp.task_2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task_2.payload.BankCardDto;
import uz.pdp.task_2.payload.Result;
import uz.pdp.task_2.service.CardService;


@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addCard(@RequestBody BankCardDto cardDto) {
        Result result = cardService.addCard(cardDto);
        return ResponseEntity.status(201).body(result);
    }

    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCardById(@PathVariable Integer id) {
        Result cardById = cardService.getCardById(id);
        return ResponseEntity.status(cardById != null ? HttpStatus.OK : HttpStatus.CONFLICT).body(cardById);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCard(@RequestBody BankCardDto cardDto, @PathVariable Integer id) {
        Result result = cardService.editCardById(cardDto, id);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize(value = "hasRole('BANK_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCard(@PathVariable Integer id) {
        Result result = cardService.deleteCard(id);
        return ResponseEntity.ok(result);
    }

}



