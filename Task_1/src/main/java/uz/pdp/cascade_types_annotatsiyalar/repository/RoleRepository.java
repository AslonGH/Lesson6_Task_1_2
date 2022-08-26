package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.enumClass.Role;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

     Role findByRoleName(RoleName roleName);
}
