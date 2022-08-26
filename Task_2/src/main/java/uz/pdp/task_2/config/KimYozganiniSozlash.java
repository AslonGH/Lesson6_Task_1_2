package uz.pdp.task_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration       // Class ichida Bean qilish uchun kerak
@EnableJpaAuditing   // Jpa Auditini yoqish
                     // Product ning CRUD AMALINI KIMYOZGANLIGINI QAYTARADIGAN CLASS
public class KimYozganiniSozlash {

      @Bean
      AuditorAware<Integer>auditorAware(){
         return new KimYozganiniBilish();  // BU METHOD AuditorAware TIPINI QAYTARA OLADI
      }

}
