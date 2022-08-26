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
import uz.pdp.cascade_types_annotatsiyalar.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComDirectorService {


    @Autowired
    EmpCustomerRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerDirector(UserRegisterDto registerDto){

        boolean existsByEmail3 = employeeRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail3){
            return new ApiResponse("Bunday Email Allqachon mavjud",false);
        }

          EmpCustomer director=new EmpCustomer();
        director.setFirstname(registerDto.getFirstname());
        director.setLastname(registerDto.getLastname());
        director.setEmail(registerDto.getEmail());
        director.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        director.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.COMPANY_DIRECTOR)));
        director.setEmailCode(UUID.randomUUID().toString());

//        director.setEnabled(true);
//        director.setEmailCode(null);

        employeeRepository.save(director);
        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
       sendEMail(director.getEmailCode(),director.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz,Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);
    }


   // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
    public Boolean sendEMail(String emailCode, String sendingEmail){
      try {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("test@gmail.com");  // JÖNATILADIGAN EMAIL(IXTIYORIY EMAILNI YOZSA BÖLADI)
    mailMessage.setTo(sendingEmail);
    mailMessage.setSubject("Accountni tasdiqlash");
    mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/companyDirector?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang</a>");
    javaMailSender.send(mailMessage);
    return true;

    }catch (Exception e){
      return false;
      }
}


    // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
    public ApiResponse directorVerifyEmail(String emailCode, String email){
        Optional<EmpCustomer> optionalUser = employeeRepository.findByEmailCodeAndEmail(emailCode, email);
        if (optionalUser.isPresent()){
            EmpCustomer director = optionalUser.get();
               // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
               // Userdagi setEnabled Method false edi endi shuni true qilamiz(YOQAMIZ)
               director.setEnabled(true);
               // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
               // Link b-n kelgan EmailCod DB dan topmasdan,"Account allaqachon tasdiqlangan"   xabarini qaytarsin.
               director.setEmailCode(null);
               employeeRepository.save(director);
               return new ApiResponse("Account tasdiqlandi",true);
           }
               return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }


    // BU METHOD USERNAME VA PASSWORD NI DB B-N SOLISHTIRADI VA USER ENTITYDAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.
    public ApiResponse loginDirector(LoginDto loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getEmail(), loginDto.getPassword()));

      // UserDetails dagi User ni beradi...
      //  User user = (User) authentication.getPrincipal();
        EmpCustomer user = (EmpCustomer) authentication.getPrincipal();
        // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
        String token = jwtProvider.generateToken(loginDto.getEmail(),user.getRoles());
        return new ApiResponse("Token",true,token);

    }catch (BadCredentialsException  badCredentialsException){
        return new ApiResponse("Parol yoki login xato",false);
    }}


}




