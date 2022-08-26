package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.TourniquetCardDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.TourniquetCardService;

import java.util.UUID;
@RestController
@RequestMapping("/api/tourniquetCard")
public class TourniquetCardController {


    @Autowired
    TourniquetCardService tourniquetCardService;


    @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
    @PostMapping("/add")
    public HttpEntity<?>addTourniquetCard(@RequestBody  TourniquetCardDto tourniquetCardDto){
        ApiResponse apiResponse = tourniquetCardService.addTourniquetCard(tourniquetCardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
    @GetMapping("/getById/{id}")
    public HttpEntity<?>getTourniquetCard(@PathVariable UUID id){
        ApiResponse apiResponse = tourniquetCardService.getTourniquetCardById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
    @GetMapping
    public HttpEntity<?>getTourniquetCards(){
      ApiResponse getTourniquetCards= tourniquetCardService.getTourniquetCards();
        return ResponseEntity.status(getTourniquetCards.isSuccess()?200:409).body(getTourniquetCards);
    }


    @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?>editTourniquetCard(@PathVariable UUID id, @RequestBody TourniquetCardDto tourniquetCardDto){
        ApiResponse apiResponse = tourniquetCardService.editTourniquetCard(id, tourniquetCardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?>deleteTourniquetCard(@PathVariable UUID id){
        ApiResponse apiResponse = tourniquetCardService.deleteTourniquetCard(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
