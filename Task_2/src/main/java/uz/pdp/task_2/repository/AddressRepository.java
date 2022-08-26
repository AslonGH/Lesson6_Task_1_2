package uz.pdp.task_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task_2.entity.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
