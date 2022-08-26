package uz.pdp.task_2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.enums.RoleName;
import uz.pdp.task_2.entity.enumClass.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

         Role findByRoleName(RoleName roleName);
}
