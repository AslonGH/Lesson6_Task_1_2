package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpCustomerRepository extends JpaRepository<EmpCustomer, UUID> {


    boolean existsByEmail(String email);

    boolean existsByEmailNot(String email);

    // OPTIONAL ISHLATAMIZ CHUNKI BIZGA User TIPIDAGI OBJECT QAYTARSIN. Parametr/ni örni muhim
    Optional<EmpCustomer> findByEmailCodeAndEmail(String emailCode, String email);

    Optional<EmpCustomer> findByEmail(String email);

   // Set<EmpCustomer> findByRoles(Set<Role> roles);
}
