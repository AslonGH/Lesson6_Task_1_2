package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.SimCard;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, UUID> {

  boolean existsByNumber(@Length(max = 7) String number);

  boolean existsByNumberNot(@Length(max = 7) String number);

  List<SimCard> findAllByEnabledTrue();

  Optional<SimCard> findByCustomerId(UUID uuid);
  Optional<SimCard> findByPackagesId(UUID packages_id);
  Optional<SimCard> findByEntertainmentsId(UUID entertainments_id);

    // Optional<SimCard> findByTariffId(UUID tariff_id);
    // Set<SimCard> findAllByTariffId(UUID tariff_id);
}
