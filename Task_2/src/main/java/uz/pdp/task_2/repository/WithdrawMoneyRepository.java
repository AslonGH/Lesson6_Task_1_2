package uz.pdp.task_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_2.entity.WithdrawFromCard;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
public interface WithdrawMoneyRepository extends JpaRepository<WithdrawFromCard, Integer> {

      List<WithdrawFromCard> findAllByAtmIdAndDate(Integer atm_id, LocalDate date);

      List<WithdrawFromCard> findAllByAtmId(Integer atm_id);

}
