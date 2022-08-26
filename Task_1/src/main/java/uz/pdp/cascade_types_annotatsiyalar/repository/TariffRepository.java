package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.Tariff;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, UUID> {

      List<Tariff> findAllByIsActiveTrue();

     // Optional<Tariff> findBySimCardsId(UUID simCards_id);
     Optional<Tariff>findAllByIsActiveFalse();

}
