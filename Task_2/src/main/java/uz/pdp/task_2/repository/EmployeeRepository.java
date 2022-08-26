package uz.pdp.task_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_2.entity.Employee;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByEmail(String email);

    // OPTIONAL ISHLATAMIZ CHUNKI BIZGA User TIPIDAGI OBJECT QAYTARSIN. Parametr/ni örni muhim
    Optional<Employee> findByEmailCodeAndEmail(String emailCode,  String email);

    Optional<Employee> findByEmail(String email);


}
