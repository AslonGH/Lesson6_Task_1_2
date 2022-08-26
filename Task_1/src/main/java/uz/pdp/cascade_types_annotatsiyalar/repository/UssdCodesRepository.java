package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.enumClass.UssdCods;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.UssdCodsName;

import java.util.UUID;

@Repository
public interface UssdCodesRepository extends JpaRepository<UssdCods, UUID> {

     UssdCods findByCodeName(UssdCodsName codeName);
}
