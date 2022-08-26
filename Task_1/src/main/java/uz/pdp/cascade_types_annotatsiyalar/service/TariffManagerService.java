package uz.pdp.cascade_types_annotatsiyalar.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.RoleName;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.UserRegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.FilialRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.RoleRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.TourniquetCardRepository;
import uz.pdp.cascade_types_annotatsiyalar.security.JwtProvider;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class TariffManagerService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmpCustomerRepository employeeRepository;
    @Autowired
    FilialRepository filialRepository;
    @Autowired
    TourniquetCardRepository tourniquetCardRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;
    // SecurityConfig Class idagi AuthenticationManager qaytaruvchi Methodni Autowired qilamiz.
    // USER VA PASSWORDNI AVTOMATIK AUTHENTICATE QILADIGAN CLASS
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerTariffManager(UserRegisterDto managerDto){

        // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
        boolean existsByEmail = employeeRepository.existsByEmail(managerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday Email Allqachon mavjud",false);
        }
         EmpCustomer manager=new EmpCustomer();
        manager.setFirstname(managerDto.getFirstname());
        manager.setLastname(managerDto.getLastname());
        manager.setEmail(managerDto.getEmail());
        manager.setPassword(passwordEncoder.encode(managerDto.getPassword()));
        manager.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.TARIFF_MANAGER)));
        manager.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(manager);
        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(manager.getEmailCode(), manager.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan digitalizing Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);
    }

    public ApiResponse managerVerifyEmail(String emailCode, String email){

        Optional<EmpCustomer> optionalManager = employeeRepository.findByEmailCodeAndEmail(emailCode, email);

        if (optionalManager.isPresent()){
            EmpCustomer manager = optionalManager.get();
            // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
            // Userdagi setEnabled Method false edi endi shuni true qilamiz(YOQAMIZ)
            manager.setEnabled(true);
            // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
            // Link b-n kelgan EmailCod DB dan topmasdan,"Account allaqachon tasdiqlangan"   xabarini qaytarsin.
            manager.setEmailCode(null);
            employeeRepository.save(manager);
            return new ApiResponse("Account tasdiqlandi",true);
        }
        return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }

    public Boolean sendEMail(String emailCode, String sendingEmail){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("aslon.dinov@gmail.com"); // JÖNATILADIGAN EMAIL(IXTIYORIY EMAILNI YOZSA BÖLADI)
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accountni tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/tariffManager?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse loginManager(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));

            // UserDetails dagi User ni beradi...
            EmpCustomer manager = (EmpCustomer) authentication.getPrincipal();
            // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
            String token = jwtProvider.generateToken(loginDto.getEmail(), manager.getRoles());
            return new ApiResponse("Token",true,token);

        }catch (BadCredentialsException  badCredentialsException){
            return new ApiResponse("Parol yoki login xato",false);
        }}

}






