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
import uz.pdp.cascade_types_annotatsiyalar.payload.CustomerDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.LoginDto;
import uz.pdp.cascade_types_annotatsiyalar.payload.RegisterDto;
import uz.pdp.cascade_types_annotatsiyalar.repository.EmpCustomerRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.RoleRepository;
import uz.pdp.cascade_types_annotatsiyalar.repository.SimCardRepository;
import uz.pdp.cascade_types_annotatsiyalar.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmpCustomerRepository empCustomerRepository;

    @Autowired
    SimCardRepository simCardRepository;

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


    public ApiResponse registerCustomer(CustomerDto registerDto){

        // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
        boolean existsByEmail = empCustomerRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday Email Allqachon mavjud",false);
        }

          EmpCustomer customer=new EmpCustomer();
        customer.setFirstname(registerDto.getFirstname());
        customer.setLastname(registerDto.getLastname());

        // customer.setSimCards(simCards);

        if (registerDto.getKindOffCustomer()==1){
        customer.setKindOffCustomer(RoleName.JuristicPerson.name());}
        else if(registerDto.getKindOffCustomer()==2){
        customer.setKindOffCustomer(RoleName.NaturalPerson.name());}
        else {
            return new ApiResponse( "JuristicPerson =1;  \n" +
                                            "naturalPerson  =2;",false);
        }

        customer.setEmail(registerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        customer.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.CUSTOMER)));
        customer.setEmailCode(UUID.randomUUID().toString());
        empCustomerRepository.save(customer);
        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(customer.getEmailCode(), customer.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz," +
                "Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);
    }

    public ApiResponse managerVerifyEmail(String emailCode, String email){

        Optional<EmpCustomer> optionalCustomer = empCustomerRepository.findByEmailCodeAndEmail(emailCode, email);

        if (optionalCustomer.isPresent()){
            EmpCustomer customer = optionalCustomer.get();
            // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
            // Userdagi setEnabled Method false edi endi shuni true qilamiz(YOQAMIZ)
            customer.setEnabled(true);
            // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
            // Link b-n kelgan EmailCod DB dan topmasdan,"Account allaqachon tasdiqlangan"   xabarini qaytarsin.
            customer.setEmailCode(null);
            empCustomerRepository.save(customer);
            return new ApiResponse("Account tasdiqlandi",true);
        }
        return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }

    public void sendEMail(String emailCode, String sendingEmail){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("Teat@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/customer?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang </a>");
            javaMailSender.send(simpleMailMessage);
        }catch (Exception ignore){

        }
    }

    public ApiResponse loginCustomer(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));

            // UserDetails dagi User ni beradi...
            EmpCustomer customer = (EmpCustomer) authentication.getPrincipal();
            // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
            String token = jwtProvider.generateToken(loginDto.getEmail(), customer.getRoles());
            return new ApiResponse("Token",true,token);

        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Parol yoki login xato",false);
        }}

}


