package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_2.entity.BankCard;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CardRepository extends JpaRepository<BankCard, Integer>{

     boolean existsByCVVCode3(String CVVCode3);
     boolean existsByCVVCode3AndIdNot(String CVVCode3, Integer id);

     boolean existsByNumber16(String number16);
    boolean existsByNumber16AndIdNot(String number16, Integer id);

     boolean existsByParole4(String parole4);
     boolean existsByParole4AndIdNot(String parole4, Integer id);

    //  Optional<Card> findByNumber16AndParole4(String number16, String parole4);
     Optional<BankCard> findByNumber16(String number16);

}





 // @RepositoryRestResource(path = "card",excerptProjection = CardProjection.class)