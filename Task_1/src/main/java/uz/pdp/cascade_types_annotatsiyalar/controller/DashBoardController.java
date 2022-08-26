package uz.pdp.cascade_types_annotatsiyalar.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cascade_types_annotatsiyalar.service.ApiResponse;
import uz.pdp.cascade_types_annotatsiyalar.service.DashboardService;
import uz.pdp.cascade_types_annotatsiyalar.service.SimCardService;

@RestController
@RequestMapping("/api/dashBoard")
public class DashBoardController {

    @Autowired
    SimCardService simCardService;

    @Autowired
    DashboardService dashboardService;


    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @GetMapping(value = "/get/activeSimCards")
    public HttpEntity<?> getDashBoard() {
        ApiResponse apiResponse = dashboardService.getActiveSimCards();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @GetMapping(value = "/get/dailyInCome/{day}")
    public HttpEntity<?> getReportDaily(@PathVariable String day) {
        ApiResponse apiResponse = dashboardService.getReportDailyIncome(day);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @GetMapping(value = "/get/monthlyIncome/{month}/{year}")
    public HttpEntity<?> getReportMonthly(@PathVariable Integer month, @PathVariable Integer year) {
        ApiResponse apiResponse = dashboardService.getReportMonthlyIncome(month, year);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('COMPANY_DIRECTOR')")
    @GetMapping(value = "/get/halfQuarter/{day1}/{day2}")
    public HttpEntity<?> getHalfQuarter(@PathVariable String day1, @PathVariable String day2) {
        ApiResponse apiResponse = dashboardService.getHalfQuarterIncome(day1,day2);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @GetMapping(value = "/get/reportTariff")
    public HttpEntity<?> getReportTariff() {
        ApiResponse apiResponse = dashboardService.getReportTariffs();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @GetMapping(value = "/get/reportPackage")
    public HttpEntity<?> getReportPackage() {
        ApiResponse apiResponse = dashboardService.getReportPackages();
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
}









