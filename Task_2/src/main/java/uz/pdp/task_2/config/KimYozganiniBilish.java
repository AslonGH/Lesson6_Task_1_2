package uz.pdp.task_2.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.task_2.entity.Employee;

import java.util.Optional;
import java.util.UUID;
// User ning Id sini tipi UUID, SecurityContextHolder YA'NI SYSTEMA. SYSTEMAGA KIRIB TURGAN USER NING UUID SINI SYSTEMAGA
// QAYTARAMIZ, VA U Product dagi createdBy va updatedBy Field /iga BERADI. CHUNKI ULARNING TIPI UUID.BU CLASS Product ni
// CRUD QILGANDA, DATA BASE da Product Entity dagi createdBy ustuniga Userning UUID nomeri chiqadi.

public class KimYozganiniBilish implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {

        // KIMDIR SYSTEMAGA KIRIB TURGAN USER - null BÖLMASA, Authenticated BÖLSA, anonymousUser BÖLMASA
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                  if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser"))
        {
            // SYSTEMAGA KIRGAN USERNING ID(UUID) SINI QAYTARAMIZ
            Employee user = (Employee) authentication.getPrincipal();
            return Optional.of(user.getId());
        }
        return Optional.empty();
    }


}
