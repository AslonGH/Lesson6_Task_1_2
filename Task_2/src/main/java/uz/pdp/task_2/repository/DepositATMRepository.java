package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_2.entity.DepositInATM;

import java.util.List;
import java.util.UUID;


@Repository
public interface DepositATMRepository extends JpaRepository<DepositInATM, Integer> {

    List<DepositInATM> findAllByBankomatId(Integer bankomat_id);

}
