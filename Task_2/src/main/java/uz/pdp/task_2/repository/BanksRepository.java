package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.Banks;


//@RepositoryRestResource(path = "card",excerptProjection = CardProjection.class)
public interface BanksRepository extends JpaRepository<Banks, Integer> {

}
