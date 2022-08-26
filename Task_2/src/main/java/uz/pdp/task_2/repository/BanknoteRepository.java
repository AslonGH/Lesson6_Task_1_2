package uz.pdp.task_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.enumClass.BankNote;
import uz.pdp.task_2.entity.enums.BankNoteName;

public interface BanknoteRepository extends JpaRepository<BankNote,Integer> {

    boolean existsByBankNoteName(BankNoteName banknoteName);

    BankNote findByBankNoteName(BankNoteName bankNoteName);
}
