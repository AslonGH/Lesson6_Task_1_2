package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.CompanyBudget;
import uz.pdp.cascade_types_annotatsiyalar.entity.Package;
import uz.pdp.cascade_types_annotatsiyalar.entity.SimCard;
import uz.pdp.cascade_types_annotatsiyalar.entity.Tariff;
import uz.pdp.cascade_types_annotatsiyalar.repository.BudgetRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.PackageRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.SimCardRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.TariffRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    SimCardRepository simCardRepository;
    @Autowired
    BudgetRepository budgetRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    PackageRepository packageRepository;


    public ApiResponse getActiveSimCards() {
        List<SimCard> all = simCardRepository.findAllByEnabledTrue();
        return new ApiResponse("SimCard lar soni: " + all.size(), true);
    }

    public ApiResponse getReportDailyIncome(String stringDate) {

        LocalDate localDate = LocalDate.parse(stringDate);
        List<CompanyBudget> allByLocalDate = budgetRepository.findAllByLocalDate(localDate);
        return new ApiResponse("Budget: ", true, allByLocalDate);
    }

    public ApiResponse getReportMonthlyIncome(Integer month, Integer year) {

        List<CompanyBudget> companyBudgets = new ArrayList<>();
        List<CompanyBudget> all = budgetRepository.findAll();
        for (CompanyBudget companyBudget : all) {
            if ((companyBudget.getLocalDate().getMonth().getValue() == month) && (companyBudget.getLocalDate().getYear() == year)) {
                companyBudgets.add(companyBudget);
            }
        }
        return new ApiResponse("ReportMonthly ", true, companyBudgets);
    }

    public ApiResponse getHalfQuarterIncome(String localDate, String localDate1) {

        List<CompanyBudget> allByLocalDateBetween =
                budgetRepository.findAllByLocalDateBetween(LocalDate.parse(localDate), LocalDate.parse(localDate1));
        return new ApiResponse("HalfQuarter Report", true, allByLocalDateBetween);
    }

    public ApiResponse getReportTariffs() {

         Map<String, Integer> map = new HashMap<>();
        List<String> tariffList = new ArrayList<>();

        // HAMMA SOTILGAN TARIFF LAR.
        List<Tariff> tariffs = tariffRepository.findAllByIsActiveTrue();
        for (Tariff tariff1 : tariffs) {
            tariffList.add(tariff1.getName());
        }
        String[] names = new String[tariffList.size()];
        tariffList.toArray(names);
        Arrays.sort(names);

        for (int i = 0; i < names.length - 1; i++) {

            int count = 1;
            for (int j = i + 1; j < names.length; j++) {
                if (names[i].equals(names[j])) {
                    count++;
                } else break;

                if (count > 1) {
                    map.put(names[i], count);
                    i += (count - 1);
                }
            }
        }
        return new ApiResponse("Sotilgan tarifflar va soni:  " + map, true,
                "Sotilgan tariflar: " + Arrays.toString(names));

    }

    public ApiResponse getReportPackages() {
     return new ApiResponse("Packages ", true, packageRepository.findAllByIsPackageSoldTrue());

    }
}






