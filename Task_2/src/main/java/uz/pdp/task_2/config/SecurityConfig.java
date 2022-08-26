
package uz.pdp.task_2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.task_2.security.JwtFilters;
import uz.pdp.task_2.service.AuthService;

import java.util.Properties;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


     @Autowired
     AuthService myAuthService;

     @Autowired
     JwtFilters jwtFilter;


      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
              auth.userDetailsService(myAuthService).passwordEncoder(passwordEncoder());

      }

//      @Override
//      @Bean
//      protected AuthenticationManager authenticationManager() throws Exception {
//          return super.authenticationManager();
//      }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


      @Bean
      PasswordEncoder passwordEncoder(){
          return  new BCryptPasswordEncoder();
      }


       // EMAILDAN XABAR JÖNATISH METHODI.
      @Bean
      public JavaMailSender javaMailSender(){

        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("aslon.dinov@gmail.com");
        mailSender.setPassword("keuptauhdbxfdsov");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.debug","true");
        return mailSender;
    };
}




 /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      http
     .csrf().disable()
     .authorizeRequests()
     .antMatchers("/api/auth/login").permitAll()
     .anyRequest().authenticated();

     // Spring security dependency ning  UsernamePasswordAuthenticationFilter Class idan oldin jwtFilter ishlasin!!!
     http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
     // SPRING SECURITYGA SESSIONGA USHLAB QOLMASLIGINI BUYURMOQDA(DEFAULT HOLATDA USHLAYDI).CHUNKI TOKEN ISHLATSAK HAR REQUESTDA TEKSHIRILSIN
     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER); //YOKI NEVER ÖRNIDA STATELESS QÖLLASA BÖLADI

     return http.build();
    }*/


