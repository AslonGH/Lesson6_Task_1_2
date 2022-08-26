package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.Atm;

//@RepositoryRestResource(path = "card",excerptProjection = CardProjection.class)
public interface BankATMRepository extends JpaRepository<Atm, Integer> {

}
