package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.Company;

import java.util.Optional;
import java.util.UUID;

// @RepositoryRestResource(path = "company")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

     Optional<Company> findByName(String name);
}
