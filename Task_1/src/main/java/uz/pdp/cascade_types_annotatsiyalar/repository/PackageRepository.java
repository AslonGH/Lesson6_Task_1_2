package uz.pdp.cascade_types_annotatsiyalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cascade_types_annotatsiyalar.entity.Package;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
  public interface PackageRepository extends JpaRepository<Package, UUID> {

  List<Package> findAllByIsPackageSoldTrue();


      // Optional<Package> findByTariffId_SimCardsId_CustomerIdAndTypeOfPackage(UUID tariffs_simCards_customer_id, String typeOfPackage);
      // List<Package> findBySimCardsId_CustomerIdAndTypeOfPackage(UUID tariffs_simCards_customer_id, String typeOfPackage);

      // Optional<Package> findBySimCardsIdAndTypeOfPackage(UUID simCards_id, String typeOfPackage);


       Optional<Package>findByTypeOfPackageAndId(String typeOfPackage, UUID id);

  }
