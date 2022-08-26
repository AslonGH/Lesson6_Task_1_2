package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.enumClass.CardType;
import uz.pdp.task_2.entity.enums.CardTypeName;

public interface CardTypeRepository extends JpaRepository<CardType,Integer> {
      CardType findByCardTypeName(CardTypeName roleName);
}
