//package repository;
//package uz.pdp.rest_api_jwt.repository;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import uz.pdp.rest_api_jwt.entity.User;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, UUID> {
//
//    boolean existsByEmail(String email);
//
//    // OPTIONAL ISHLATAMIZ CHUNKI BIZGA User TIPIDAGI OBJECT QAYTARSIN
//
//     Optional<User>findByEmailAndEmailCode(String email, String emailCode);
//
//    // LOGIN QILGANDA USERNI DB DAN QIDIRADI
//    Optional<User> findByEmail(String email);
//
//}
