package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.CompanyBudget;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<CompanyBudget, UUID> {

      List<CompanyBudget>findAllByLocalDate(LocalDate localDate);

      List<CompanyBudget> findAllByLocalDateBetween(LocalDate localDate, LocalDate localDate2);

}
