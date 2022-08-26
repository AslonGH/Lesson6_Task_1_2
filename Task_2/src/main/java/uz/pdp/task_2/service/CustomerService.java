
package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.entity.Employee;
import uz.pdp.task_2.entity.enums.RoleName;
import uz.pdp.task_2.payload.LoginDtoo;
import uz.pdp.task_2.payload.RegisterDtoo;
import uz.pdp.task_2.repository.EmployeeRepository;
import uz.pdp.task_2.repository.RoleRepository;
import uz.pdp.task_2.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


//CLASSIMIZ TIPINI UserDetailsService TIPIGA AYLANTIRAMIZ
@Service
public class CustomerService {

      @Autowired
      EmployeeRepository employeeRepository;

      @Autowired
      RoleRepository roleRepository;

      @Autowired
      JavaMailSender javaMailSender;


      @Autowired
      PasswordEncoder passwordEncoder;

      @Autowired
      AuthenticationManager authenticationManager;


      public ApiResponse registerCustomer(RegisterDtoo registerDto) {

      // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
      boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
      if (existsByEmail) {
      return new ApiResponse("Bunday Email Allqachon mavjud", false);
      }
      Employee user = new Employee();
      user.setFirstname(registerDto.getFirstname());
      user.setLastname(registerDto.getLastname());
      user.setEmail(registerDto.getEmail());
      user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
      user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.CUSTOMER)));
      // Collections.singleton- bitta object ni Collection qilib beradi.
      // Set<> Collectiondan voris olgan, shu u-n  Set örnida Collection bersak böladi
      user.setEmailCode(UUID.randomUUID().toString());
      employeeRepository.save(user);
      // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
      sendEMail(user.getEmail(), user.getEmailCode());
      return new ApiResponse("User saqlandi. Accountning aktivlashtirilishi uchun emailingizni tasdiqlang", true);
      }

      // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
      public Boolean sendEMail(String sendingEmail, String emailCode) {
      try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom("aslon.dinov@gmail.com"); //qaysi emaildan jönatilishi(IXTIYORIY EMAILNI YOZSA BÖLADI)
      mailMessage.setTo(sendingEmail);
      mailMessage.setSubject("Accountni tasdiqlash");
      mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/customer?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang </a>");
      javaMailSender.send(mailMessage);
      return true;

      } catch (Exception e) {
      return false;
      }
      }

      // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
      public ApiResponse verifyEmail(String emailCode, String email) {

      Optional<Employee> optionalUser = employeeRepository.findByEmailCodeAndEmail(email, emailCode);
      if (optionalUser.isPresent()) {
      Employee user = optionalUser.get();
      // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
      // Userdagi setEnabled Method false edi, endi shuni true qilamiz(YOQAMIZ)
      user.setEnabled(true);
      // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
      // Link b-n kelgan EmailCode DB dan topmasdan,"Account allaqachon tasdiqlangan"  xabarini qaytarsin.
      user.setEmailCode(null);
      employeeRepository.save(user);
      return new ApiResponse("Account tasdiqlandi", true);
      }
      return new ApiResponse("Account allaqachon tasdiqlangan", false);
      }

      public ApiResponse loginCustomer(LoginDtoo loginDto) {

      try {
      // authenticationManager.authenticate: PASTDAGI loadUserByUsername METHODI YORDAMIDA USERNAMENI AuthService Classi uchun DB DAN TOPIB,
      // BERADI,VA BU CLASS UserDetails QAYTARADI VA BU UserDetails ni SecurityConfig CLASSIDAGI configure Methodi ENCODLAB BASA BILAN SOLISHTIRADI.
      // AGAR TÖGRI BÖLSA USER ENTITY DAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.AGAR BIRORTASI FALSE BÖSA USER SYSTEMAGA KIROLMAYDI.
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      loginDto.getUsername(), loginDto.getPassword()));
      // HAMMASI YAXSHI BÖLSA,SHU YERGA TUSHADI,VA TOKEN GENERATION QILAMIZ
      // UserDetails dagi User ni beradi...Shu User ning Role sini olamiz.
      Employee user =(Employee) authentication.getPrincipal();
      // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ; KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI YOKI YOPIQ YÖL/GA MUROJAAT QILADI:
      String token = JwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
      return new ApiResponse("Token",true, token);

      }catch (BadCredentialsException badCredentialsException){
      return new ApiResponse("Parol yoki login xato",false);
      }

      }


    }

