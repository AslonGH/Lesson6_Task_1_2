package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.TourniquetCardHistory;

import java.util.UUID;

@Repository
public interface TourniquetHistoryRepository extends JpaRepository<TourniquetCardHistory, UUID> {


    // BERILGAN VAQT ORALIGIDA ISHGA KELIB KETISHI


   // List<TourniquetCardHistory> findAllByCard_EmployeeIdAndGetOutTimeBetween(UUID card_employee_id, Timestamp getOutTime, Timestamp getOutTime2);

   //  List<TourniquetCardHistory> findAllByCard_EmployeeIdAndGetInTimeBetween(UUID card_employee_id, Timestamp getInTime, Timestamp getInTime2);

}
