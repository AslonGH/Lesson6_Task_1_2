package uz.pdp.cascade_types_annotatsiyalar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.payload.TourniquetHistoryDto;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.TourniquetHistoryService;

import java.util.UUID;


@RestController
@RequestMapping("/api/tourniquetHistory")
public class TourniquetHistoryController {


    @Autowired
    TourniquetHistoryService tourniquetHistoryService;


    @PreAuthorize(value ="hasAnyRole('FILIAL_DIRECTOR','MANAGER_OF_FILIAL')")
    @GetMapping("/{id}")
    public HttpEntity<?>getTourniquetHistory(@PathVariable UUID id){
        ApiResponse tourniquetHistory = tourniquetHistoryService.getTourniquetHistory(id);
        return ResponseEntity.status(tourniquetHistory.isSuccess()?200:409).body(tourniquetHistory);
    }


    @PreAuthorize(value ="hasAnyRole('FILIAL_DIRECTOR','MANAGER_OF_FILIAL')")
    @GetMapping
    public HttpEntity<?>getTourniquetHistories(){
        ApiResponse getTourniquetCards= tourniquetHistoryService.getTourniquetHistories();
        return ResponseEntity.status(getTourniquetCards.isSuccess()?200:409).body(getTourniquetCards);
    }


    @PreAuthorize(value ="hasAnyRole('FILIAL_DIRECTOR','MANAGER_OF_FILIAL')")
    @PostMapping("/add")
    public HttpEntity<?>addTourniquetHistory(@RequestBody TourniquetHistoryDto historyDto){
        ApiResponse apiResponse = tourniquetHistoryService.addTourniquetHistory(historyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('FILIAL_DIRECTOR','MANAGER_OF_FILIAL')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?>editTourniquetHistory(@PathVariable UUID id, @RequestBody TourniquetHistoryDto historyDto){
        ApiResponse apiResponse = tourniquetHistoryService.editTourniquetHistory(id, historyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('FILIAL_DIRECTOR','MANAGER_OF_FILIAL')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?>deleteTourniquetHistory(@PathVariable UUID id){
        ApiResponse apiResponse = tourniquetHistoryService.deleteTourniquetHistory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
