package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.InfosEntertainment;

import java.util.List;
import java.util.UUID;

@Repository
public interface InfosEntertainmentRepository extends JpaRepository<InfosEntertainment, UUID> {

    // SOTILGAN XIZMATLAR RÖYXATI
    List<InfosEntertainment> findAllByIsActiveTrue();
}
