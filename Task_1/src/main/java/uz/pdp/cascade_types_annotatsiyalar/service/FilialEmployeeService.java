package uz.pdp.cascade_types_annotatsiyalar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.cascade_types_annotatsiyalar.controller.FilialEmployeeController;
import uz.pdp.cascade_types_annotatsiyalar.entity.EmpCustomer;
import uz.pdp.cascade_types_annotatsiyalar.entity.enums.RoleName;
import uz.pdp.cascade_types_annotatsiyalar.payload.UserRegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.RegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.FilialRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.RoleRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.TourniquetCardRepository;
import uz.pdp.cascade_types_annotatsiyalar.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class FilialEmployeeService {


    @Autowired
    EmpCustomerRepository employeeRepository;

    @Autowired
    FilialRepository filialRepository;
    @Autowired
    TourniquetCardRepository tourniquetCardRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    FilialEmployeeController filialEmployeeController;


      // TIZIM GA KIRIB TURGAN EMPLOYEE
    public UUID getEmployeeById(){
          EmpCustomer principal = (EmpCustomer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          return principal.getId();
          /*  SpringSecurityAuditAwareImpl springSecurityAuditAware=new SpringSecurityAuditAwareImpl();
          if (springSecurityAuditAware.getCurrentAuditor().isPresent()) return springSecurityAuditAware.getCurrentAuditor().get();
          else return null;*/
      }

    public ApiResponse registerEmployee(UserRegisterDto registerDto){

        boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday Email Allqachon mavjud",false);
        }

           EmpCustomer employee=new EmpCustomer();
        employee.setFirstname(registerDto.getFirstname());
        employee.setLastname(registerDto.getLastname());
        employee.setEmail(registerDto.getEmail());
        employee.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        employee.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.FILIAL_EMPLOYEE)));
        employee.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);

        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(employee.getEmailCode(),employee.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz,Accountning aktivlashtirilishi uchun" +
                                                                          " emailingizni tasdiqlang",true);
    }

    public ApiResponse getFilialEmployeeById(UUID uuid) {
         if (!uuid.equals(getEmployeeById()) )
         return new ApiResponse("Siz uchun bu ma'lumotga ruxsat yöq",false);
         Optional<EmpCustomer> byId = employeeRepository.findById(uuid);
        return byId.map(employee -> new ApiResponse("", true, employee)).orElseGet(() -> new ApiResponse("Employee not found", false));
    }

    public ApiResponse editFilialEmployee(UUID id, RegisterDto registerDto) {

        if ( !id.equals(getEmployeeById()) )
        return new ApiResponse("Siz uchun bu ma'lumotga ruxsat yöq",false);

        Optional<EmpCustomer> byId = employeeRepository.findById(id);
        if (!byId.isPresent())
        return new ApiResponse("Employee not found",false);

        boolean exists = employeeRepository.existsByEmailNot(registerDto.getEmail());
        if (exists)
        return new ApiResponse("Such email already exist",false);

          EmpCustomer employee = byId.get();
        employee.setFirstname(registerDto.getFirstname());
        employee.setLastname(registerDto.getLastname());
        employee.setEmail(registerDto.getEmail());
        employee.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        employee.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.FILIAL_EMPLOYEE)));
        employee.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);

        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(employee.getEmail(), employee.getEmailCode());
       return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz,Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);

    }

    public ApiResponse deleteFilialEmployee(UUID uuid) {
        Optional<EmpCustomer> byId = employeeRepository.findById(uuid);
        if (!byId.isPresent()){
            return new ApiResponse("Employee topilmadi",false);
        }
        try {
            employeeRepository.deleteById(uuid);
            return new ApiResponse("Employee öchirildi",true);
        }catch (Exception e){
            return new ApiResponse("Employee öchirilmadi",true);
        }
    }

    // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
    public Boolean sendEMail(String emailCode,String sendingEmail){
      try {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("aslon.dinov@gmail.com"); // JÖNATILADIGAN EMAIL(IXTIYORIY EMAILNI YOZSA BÖLADI)
    mailMessage.setTo(sendingEmail);
    mailMessage.setSubject("Accountni tasdiqlash");
    mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/filialEmployee?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang</a>");
    javaMailSender.send(mailMessage);
    return true;
    }catch (Exception e){
      return false;
      }
}

    // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
    public ApiResponse employeeVerifyEmail(String emailCode,String email){
        Optional<EmpCustomer> optionalUser = employeeRepository.findByEmailCodeAndEmail(emailCode,email);
        if (optionalUser.isPresent()){
            EmpCustomer employee = optionalUser.get();
               // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
               // Userdagi setEnabled Method false edi endi shuni true qilamiz(YOQAMIZ)
               employee.setEnabled(true);
               // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
               // Link b-n kelgan EmailCod DB dan topmasdan,"Account allaqachon tasdiqlangan"   xabarini qaytarsin.
               employee.setEmailCode(null);
               employeeRepository.save(employee);
               return new ApiResponse("Account tasdiqlandi",true);
           }
               return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }

    // BU METHOD USERNAME VA PASSWORD NI DB B-N SOLISHTIRADI VA USER ENTITYDAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.
    public ApiResponse loginEmployee(LoginDto loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getEmail(), loginDto.getPassword()));
      //  UserDetails dagi User ni beradi...
      //  User user = (User) authentication.getPrincipal();
        EmpCustomer user = (EmpCustomer) authentication.getPrincipal();
        // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
        String token = jwtProvider.generateToken(loginDto.getEmail(),user.getRoles());
        return new ApiResponse("Token",true,token);
    }catch (BadCredentialsException  badCredentialsException){
        return new ApiResponse("Parol yoki login xato",false);
    }}
}


