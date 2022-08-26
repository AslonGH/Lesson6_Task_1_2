




/*     for (EmpCustomer filialEmployee : filial.getFilialEmployees()) {
       filialEmployee.setId(this.id);
   }
   filial.setFilialEmployees((Set<EmpCustomer>) this);
*/
    /* public void setFilial1(Filial filial) {
        filial.setFilialDirector(this);
        this.filial = filial;
    }

    public void setFilial2(Filial filial) {
        filial.setFilialManager(this);
        this.filial = filial;
    }

    public void setFilial3(Filial filial) {
        Set<EmpCustomer> filialInternManagers = filial.getFilialInternManagers();
        filialInternManagers.add(this);
        this.filial = filial;
    }
    */
    /*
    // tourniquetCard ichidagi EmpCustomer u-n SHU  EmpCustomer ni beramiz
    @OneToOne(cascade = CascadeType.ALL)
    private TourniquetCard tourniquetCard;

    public void setTourniquetCard(TourniquetCard tourniquetCard) {
        tourniquetCard.setEmpCustomer(this);
        this.tourniquetCard = tourniquetCard;
    }


      @JsonIgnore
      @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      private Set<SimCard> simCards;

      //simCards ichidagi EmpCustomer u-n SHU  EmpCustomer ni beramiz
      public void setSimCards(Set<SimCard> simCards) {

        for (SimCard simCard : simCards) {
            simCard.setCustomer(this);
        }
        this.simCard = simCard;
    }

     @ManyToMany
     private List<InfosEntertainmentService> services;
  */
    /*
    public void setSimCard(SimCard simCard) {
        for (InfosEntertainment entertainment : simCard.getEntertainments()) {
            entertainment.setSimCard(this.simCard);
        }
    // simCard.setEntertainments(Collections.singleton(this));
     this.simCard = simCard;
    }
*/
//    @JsonIgnore   // SimCard ichida InfosEntertainment bor shu uchun
//    @ManyToMany(mappedBy = "entertainments", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<SimCard> simCards;
    /*
    // BIRINCHI QÖSHISHDA SIMCARD BERILMAYDI; KEYIN PUT QILINADI, YOKI SimCardService va UseServiceService larda
    // Package ni sotilgandan keyin, sotib olgan simCard ni qöshamiz
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SimCard> simCards;

    @JsonIgnore
    @ManyToMany(mappedBy = "packages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tariff> tariffs;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SimCard> simCards;
*/
    /*
    @OneToOne(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  SimCard simCard;
    // BIRINCHI QÖSHISHDA SIMCARD BERILMAYDI; KEYIN PUT QILINADI, YOKI SimCardService va UseServiceService larda
    // Tariff  sotilgandan keyin, sotib olgan simCard ni qöshamiz
    @JsonIgnore   // SimCard ichida Tariff borligi uchun
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SimCard> simCards;
    public void setSimCards(Set<SimCard> simCards) {
        for (SimCard simCard : simCards) {
            simCard.setTariff(this);
        }
        this.simCards = simCards;
    }*/
    /*
      @JsonIgnore   // SimCard ichida Package bor shu uchun
      @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      private Set<Package> packages;

     // @JsonIgnore SimCard ichida Tariff bor bölsa
     Aslida shu uuid lik  tariff bir nechta
     sim cartaga beriladi.boshqa simcartga ham shu uuid lik tariffe berilishi mumkin shu sabab  @OneToMany böladi

     @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private Set<SimCard> simCards;

   */
    /*
    @OneToOne(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  SimCard simCard;
    // BIRINCHI QÖSHISHDA SIMCARD BERILMAYDI; KEYIN PUT QILINADI, YOKI SimCardService va UseServiceService larda
    // Tariff  sotilgandan keyin, sotib olgan simCard ni qöshamiz
    @JsonIgnore   // SimCard ichida Tariff borligi uchun
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SimCard> simCards;
    public void setSimCards(Set<SimCard> simCards) {
        for (SimCard simCard : simCards) {
            simCard.setTariff(this);
        }
        this.simCards = simCards;
    }*/
    /*
      @JsonIgnore   // SimCard ichida Package bor shu uchun
      @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
      private Set<Package> packages;

     // @JsonIgnore SimCard ichida Tariff bor bölsa
     Aslida shu uuid lik  tariff bir nechta
     sim cartaga beriladi.boshqa simcartga ham shu uuid lik tariffe berilishi mumkin shu sabab  @OneToMany böladi

     @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private Set<SimCard> simCards;

   */


/*

    @PostMapping(value = "/login/manager")
    public HttpEntity<?> loginManager(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = filialService.loginManager(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping(value = "/verifyEmail/manager")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email,@RequestParam String password) {
        ApiResponse apiResponse = filialService.managerVerifyEmail(emailCode, email, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
*/
/*

    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PutMapping(value = "/edit/dashBoard/{id}")
    public HttpEntity<?> editAction(@PathVariable UUID id, @RequestBody ActionDto actionDto) {
        ApiResponse apiResponse = dashboardService.editDashBoard(id, actionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PostMapping(value = "/add/dashBoard")
    public HttpEntity<?> addAction(@RequestBody ActionDto actionDto) {
        ApiResponse apiResponse = actionService.addAction(actionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
*/

/*
    @PreAuthorize(value ="hasAnyRole('FILIAL_MANAGER','FILIAL_DIRECTOR')")
    @GetMapping(value = "/get/filial")
    public HttpEntity<?> getFilial() {
      ApiResponse apiResponse = filialService.getFilial();
      return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
*/
/*

    @PostMapping(value = "/login/manager")
    public HttpEntity<?> loginManager(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = filialService.loginManager(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping(value = "/verifyEmail/manager")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email,@RequestParam String password) {
        ApiResponse apiResponse = filialService.managerVerifyEmail(emailCode, email, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
*/
    /*
    @PreAuthorize(value ="hasAnyRole('FILIAL_MANAGER','FILIAL_DIRECTOR')")
    @GetMapping("/get/filialDirector")
    public HttpEntity<?>getDirector(){
        ApiResponse apiResponse = filialDirectorService.getDirector();
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

     @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
     @PutMapping("/edit/filialDirector")
     public HttpEntity<?>editDirector(@RequestBody RegisterDto registerDto,@RequestParam UUID uuid){
        ApiResponse apiResponse = filialDirectorService.editDirector(registerDto,uuid);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
     }


     @PreAuthorize(value ="hasRole('FILIAL_MANAGER')")
     @DeleteMapping("/delete/filialDirector")
     public HttpEntity<?>deleteDirector(@RequestBody UUID uuid){
        ApiResponse apiResponse = filialDirectorService.deleteDirector(uuid);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
     }
*/
/*
    @PostMapping(value = "/login/manager")
    public HttpEntity<?> loginManager(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = filialService.loginManager(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping(value = "/verifyEmail/manager")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email,@RequestParam String password) {
        ApiResponse apiResponse = filialService.managerVerifyEmail(emailCode, email, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
*/
/*
    @PreAuthorize(value ="hasAnyRole('COMPANY_DIRECTOR','TARIFF_MANAGER')")
    @PutMapping(value = "/edit/action/{id}")
    public HttpEntity<?> editAction(@PathVariable UUID id, @RequestBody UseServicesDto actionDto) {
        ApiResponse apiResponse = actionService.editAction(id, actionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }
*/




 /*    public void setaPackages(Set<Package> aPackages) {
     for (Package aPackage : aPackages) {
         aPackage.setSimCards(Collections.singleton(this));
     }
     this.aPackages = aPackages;
 }*/
 /*
   // optional = false bölgani uchun,customer ga qiymat berish shart, EmpCustomer ichidagi  hali save bölmagan
   // simCard ni söraydi shunda shuni beramiz.customer ichida SimCard bor, rekusiya bölmasligi u-n  @JsonIgnore qölllaymiz
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private EmpCustomer customer;

    public void setCustomer(EmpCustomer customer) {
        Set<SimCard> simCards = customer.getSimCards();
        simCards.add(this);
        this.customer = customer;
    }
*/
/*
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SimCard     simCardTo;

     @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private List<SimCard> simCards;        // QAYSI SIM_CARTA EGASI BAJARDI
*/
/*
package uz.pdp.cascade_types_annotatsiyalar;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String [] names =new String[]{"SMS","SMS","MB","MB","SMS"};
        Arrays.sort(names);
        for (String name : names) {
            System.out.println(name);
        }

    }
}
*/

         /*
          @Autowired
          PasswordEncoder passwordEncoder;

         @Autowired
          RoleRepository roleRepository;

          @Autowired
          JavaMailSender javaMailSender;

          // SecurityConfig Class idagi AuthenticationManager qaytaruvchi Methodni Autoweired qilamiz.
          // USER VA PASSWORDNI AVTOMATIK AUTHENTICATE QILADIGAN CLASS
          @Autowired
          AuthenticationManager authenticationManager;

          @Autowired
          JwtProvider jwtProvider;
      */
         /* public ApiResponse registerUser(RegisterDto registerDto){

              // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
              boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
              if (existsByEmail) {
                  return new ApiResponse("Bunday Email Allqachon mavjud", false);
              }
                EmpCustomer user = new EmpCustomer();
              user.setFirstname(registerDto.getFirstname());
              user.setLastname(registerDto.getLastname());
              user.setEmail(registerDto.getEmail());
              user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

              // Collections.singleton- bitta object ni Collection qilib beradi.
              // Set<> Collectiondan voris olgan, shu u-n  Set örnida Collection bersak böladi

              user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.FILIAL_EMPLOYEE)));
              user.setEmailCode(UUID.randomUUID().toString());
              employeeRepository.save(user);
              // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
              sendEMail(user.getEmail(), user.getEmailCode());
              return new ApiResponse("User saqlandi. Accountning aktivlashtirilishi uchun emailingizni tasdiqlang", true);
          }


          // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
          public Boolean sendEMail(String sendingEmail, String emailCode){
              try {
                  SimpleMailMessage mailMessage = new SimpleMailMessage();
                  mailMessage.setFrom("aslon.dinov@gmail.com"); //qaysi emaildan jönatilishi(IXTIYORIY EMAILNI YOZSA BÖLADI)
                  mailMessage.setTo(sendingEmail);
                  mailMessage.setSubject("Accountni tasdiqlash");
                  mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang</a>");
                  javaMailSender.send(mailMessage);
                  return true;

              } catch (Exception e) {
                  return false;
              }
          }


          // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
          public ApiResponse verifyEmail(String emailCode, String email) {

              Optional<EmpCustomer> optionalUser = employeeRepository.findByEmailCodeAndEmail(emailCode,email);
              if (optionalUser.isPresent()) {
                  EmpCustomer user = optionalUser.get();
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


          public ApiResponse login(LoginDto loginDto) {
          try {
          // authenticationManager.authenticate: PASTDAGI loadUserByUsername METHODI YORDAMIDA USERNAMENI AuthService Classi uchun DB DAN TOPIB,
          // BERADI,VA BU CLASS UserDetails QAYTARADI.VA BU UserDetails ni SecurityConfig CLASSIDAGI configure Methodi ENCODLAB BASA BILAB SOLISHTIRADI.
          // AGAR TÖGRI BÖLSA USER ENTITY DAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.AGAR BIRORTASI FALSE BÖSA USER SYSTEMAGA KIROLMAYDI
              Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              loginDto.getUsername(), loginDto.getPassword()));

          // HAMMASI YAXSHI BÖLSA,SHU YERGA TUSHADI,VA TOKEN GENERATION QILAMIZ
          // UserDetails dagi User ni beradi...Shu User ning Role sini olamiz.
              EmpCustomer user =(EmpCustomer) authentication.getPrincipal();

          // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI YOKI YOPIQ YÖL/GA MUROJAAT QILADI:
              String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
              return new ApiResponse("Token",true, token);

          }catch (BadCredentialsException badCredentialsException){
              return new ApiResponse("Parol yoki login xato",false);
          }
       }
      */
      /*  Set<Filial> filialSet = new HashSet<>();
              List<UUID> companyDtoFilialUUIDs = companyDto.getFilialUUIDs();
              for (UUID uuid : companyDtoFilialUUIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(uuid);
                  if (!optionalFilial.isPresent())
                      return new ApiResponse("Filial not found", false);
                  filialSet.add(optionalFilial.get());
              }
              company.setFilial(filialSet);*/
      /*
               Set<Filial> filialSet = new HashSet<>();

              List<UUID> companyDtoFilialUUIDs = companyDto.getFilialUUIDs();
              for (UUID uuid : companyDtoFilialUUIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(uuid);
                  if (!optionalFilial.isPresent())
                  return new ApiResponse("Filial not found", false);
                  filialSet.add(optionalFilial.get());
              }
              company.setFilial(filialSet);*/
      /*  Set<UUID> simCardsIDs = registerDto.getSimCardsIDs();
              Set<SimCard> simCards = new HashSet<>();
              for (UUID simCardsID : simCardsIDs) {
                  Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardsID);
                  if (!optionalSimCard.isPresent()) return new ApiResponse("SimCard not found",false);
                  simCards.add(optionalSimCard.get());
              }*/
      //    int value = localDate.getMonth().getValue();
      //    List<CompanyBudget> allByLocalDate_month = budgetRepository.findAllByLocalDate_MonthValue(value);
      //    Month month = LocalDate.parse(localDate).getMonth();
      //    List<BudgetOfCompany> allByLocalDate_month = budgetRepository.findAllByLocalDate_MonthValue(month);
      //    double monthIncome=0;
      //    monthIncome=monthIncome+companyBudget.getBalance();
      /*  List<UUID>list=new ArrayList<>(Arrays.asList(detailingDto.getSimCardsUUID()));
              List<SimCard>simCards=new ArrayList<>();
              for (UUID uuid : list) {
                  Optional<SimCard> optionalSimCard = simCardRepository.findById(uuid);
                  if (!optionalSimCard.isPresent())
                      return new ApiResponse("SimCard with UUID " + (optionalSimCard) + " not found", false);
                  simCards.add(optionalSimCard.get());
              }


            List<UUID>list=new ArrayList<>(Arrays.asList(detailingDto.getSimCardsUUID()));
              List<SimCard>simCards=new ArrayList<>();
              for (UUID uuid : list)
              {
                  Optional<SimCard> optionalSimCard = simCardRepository.findById(uuid);
                  if (!optionalSimCard.isPresent())
                      return new ApiResponse("SimCard with UUID " + (optionalSimCard) + " not found", false);
              simCards.add(optionalSimCard.get());
              }
         */
      /*       Set<UUID> filialIDs = registerDto.getFilialIDs();
              Set<Filial> filials = new HashSet<>();

              for (UUID filialID : filialIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(filialID);
                  if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);
              filials.add(optionalFilial.get());
              }
               Optional<Filial> optionalFilial = filialRepository.findById( registerDto.getFilialID());
              if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + registerDto.getFilialID(), false);
      */
       /*  public ApiResponse getDirector() {

              Set<EmpCustomer> roles = employeeRepository.findByRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.COMPANY_DIRECTOR)));
              for (Employee employee : byRoles1) {
                  if (!employee.isEnabled())
                      return new ApiResponse("Employee not found", false);
               }
              return new ApiResponse("Employee found", true, roles);
         }

        public ApiResponse editDirector(RegisterDto registerDto, UUID uuid) {

              Optional<EmpCustomer> byId = employeeRepository.findById(uuid);
              if (!byId.isPresent()){
                  return new ApiResponse("Employee topilmadi",false);
              }

              EmpCustomer director = byId.get();
              director.setFirstname(registerDto.getFirstname());
              director.setLastname(registerDto.getLastname());

              boolean existsByEmail3 = employeeRepository.existsByEmailNot(registerDto.getEmail());
              if (existsByEmail3){
                  return new ApiResponse("Bunday Email Allqachon mavjud",false);
              }

              director.setEmail(registerDto.getEmail());
              director.setPassword(passwordEncoder.encode(registerDto.getPassword()));


              //  director.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.DIRECTOR)));
              director.setEmailCode(UUID.randomUUID().toString());
              employeeRepository.save(director);

              // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
              sendEMail(director.getEmail(), director.getEmailCode());
              return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz,Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);

          }


          public ApiResponse deleteDirector(UUID uuid) {

              Optional<EmpCustomer> byId = employeeRepository.findById(uuid);
              if (!byId.isPresent()){
                  return new ApiResponse("Filial director topilmadi",false);
              }
              try {
                  employeeRepository.deleteById(uuid);
                  return new ApiResponse("Filial director öchirildi",true);
              }catch (Exception e){
                  return new ApiResponse("Filial director öchirilmadi",true);
              }
          }
      */
      /* employee.setFilial(optionalFilial.get());
       employee.setTourniquetCard(optionalTourniquetCard.get());
       employee.setFilial(optionalFilial.get());
       employee.setTourniquetCard(optionalTourniquetCard.get());
       */
      /*  Set<UUID> filialIDs = registerDto.getFilialIDs();
              Set<Filial> filials = new HashSet<>();

              for (UUID filialID : filialIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(filialID);
                  if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);
                  filials.add(optionalFilial.get());
              }

              Optional<Filial> optionalFilial = filialRepository.findById( registerDto.getFilialID());
              if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + registerDto.getFilialID(), false);

              Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(registerDto.getTourniquetCardID());
              if (!optionalTourniquetCard.isPresent()){
                  return new ApiResponse("tourniquetCard not found",false);
              }
      */
      /*
              Set<UUID> filialIDs = registerDto.getFilialIDs();
              Set<Filial> filials = new HashSet<>();

              for (UUID filialID : filialIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(filialID);
                  if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);
                  filials.add(optionalFilial.get());
              }
               Optional<Filial> optionalFilial = filialRepository.findById( registerDto.getFilialID());
              if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + registerDto.getFilialID(), false);

              Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(registerDto.getTourniquetCardID());
              if (!optionalTourniquetCard.isPresent()){
                  return new ApiResponse("tourniquetCard not found",false);
              }
      */
      /*
          @Autowired
          RoleRepository roleRepository;
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


          public ApiResponse registerManager(RegisterDto registerDto){

              // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
              boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
              if (existsByEmail){
                  return new ApiResponse("Bunday Email Allqachon mavjud",false);
              }

                Employee manager=new Employee();
              manager.setFirstname(registerDto.getFirstname());
              manager.setLastname(registerDto.getLastname());
              manager.setEmail(registerDto.getEmail());
          //  manager.setPassword(passwordEncoder.encode(registerDto.getPassword()));
          //  manager.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.MANAGER)));
              manager.setEmailCode(UUID.randomUUID().toString());
              employeeRepository.save(manager);
              // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
              sendEMail(manager.getEmailCode(), manager.getEmail());

              return new ApiResponse("Muvaffaqiyatli röyxatdan digitalizing Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);
          }

          public ApiResponse managerVerifyEmail(String emailCode, String email, String password){

              Optional<Employee> optionalManager = employeeRepository.findByEmailCodeAndEmail(emailCode, email);

              if (optionalManager.isPresent()){
                  Employee manager = optionalManager.get();
                  // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
                  // Userdagi setEnabled Method false edi endi shuni true qilamiz(YOQAMIZ)
                  manager.setEnabled(true);
                  // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
                  // Link b-n kelgan EmailCod DB dan topmasdan,"Account allaqachon tasdiqlangan"   xabarini qaytarsin.
                  manager.setEmailCode(null);
                  manager.setPassword(passwordEncoder.encode(password));
                  employeeRepository.save(manager);
                  return new ApiResponse("Account tasdiqlandi",true);
              }
              return new ApiResponse("Account allaqachon tasdiqlangan",false);
          }

          public void sendEMail(String emailCode, String sendingEmail){

              RedirectView redirectView=new RedirectView("");
              String text="http://localhost:63342/REST_API_JWT/Email.html?_ijt=gr1pki450mh63m4scvujclhsur&_ij_reload=RELOAD_ON_SAVE";


              String link = "http://localhost:8080/api/auth/verifyEmail/manager?emailCode=" + emailCode + "&email=" + sendingEmail;
              String body = "<form action="+link+" method=\"post\">\n" +
                      "<label>Create password for your cabinet</label>" +
                      "<br/><input type=\"text\" name=\"password\" placeholder=\"password\">\n" +
                      "<br/> <button>Submit</button>\n" +
                      "</form>";
              try {
                  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                  simpleMailMessage.setFrom("Teat@gmail.com");
                  simpleMailMessage.setTo(sendingEmail);
                  //    simpleMailMessage.setText(text);
                 simpleMailMessage.setText(body);
                  javaMailSender.send(simpleMailMessage);
              }catch (Exception ignore){

              }
          }


          public ApiResponse loginManager(LoginDto loginDto) {
              try {
                  Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                          loginDto.getUsername(), loginDto.getPassword()));

                  // UserDetails dagi User ni beradi...
                  Employee manager = (Employee) authentication.getPrincipal();
                  // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
                  String token = jwtProvider.generateToken(loginDto.getUsername(), manager.getRoles());
                  return new ApiResponse("Token",true,token);

              }catch (BadCredentialsException  badCredentialsException){
                  return new ApiResponse("Parol yoki login xato",false);
              }}


          @Override  // Login BÖLGANDA USERNI(TAKRORLANMAS EMAIL) DB DAN QIDIRISH
          public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           return employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+"topilmadi"));
          }

      */
      /*
                UUID filialID = managerDto.getFilialID();
              Optional<Filial> optionalFilial = filialRepository.findById(filialID);
              if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);

              Set<Filial> filials = new HashSet<>();
              for (UUID filialID : filialIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(filialID);
                  if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);
                  filials.add(optionalFilial.get());
              }


              Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(managerDto.getTourniquetCardID());
              if (!optionalTourniquetCard.isPresent()){
                  return new ApiResponse("tourniquetCard not found",false);
              }
      */
      /*
                // POST QILAYOTGAN FILIAL DIRECTORNING ID SI SHU MANAGER QÖSHILAYOTGAN FILIALNI DIRECTORIMI TOP! BU U-N
      // manager.setFilial(optionalFilial.get()); BÖILISHI K-K; setFilial DAGI FILIAL DIRECTORI ID SI SHU
      // FILIALDIRECTOR ID SI B-N BIR XILMI ????

                UUID filialID = managerDto.getFilialID();
              Optional<Filial> optionalFilial = filialRepository.findById(filialID);
              if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);

              Set<Filial> filials = new HashSet<>();
              for (UUID filialID : filialIDs) {
                  Optional<Filial> optionalFilial = filialRepository.findById(filialID);
                  if (!optionalFilial.isPresent()) return new ApiResponse("Filial not found " + filialID, false);
                  filials.add(optionalFilial.get());
              }

               Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(managerDto.getTourniquetCardID());
              if (!optionalTourniquetCard.isPresent()){
                  return new ApiResponse("tourniquetCard not found",false);
              }
           */
      /*   List<UUID> simCardIDs = packageDto.getSimCardIDs();
                  Set<SimCard> simCards = new HashSet<>();

                  for (UUID simCardID : simCardIDs)
                  {
                     Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardID);
                     if (!optionalSimCard.isPresent()){ return new ApiResponse("SimCard not found", false);}
                     else { simCards.add(optionalSimCard.get()); }
                  }
             */
      /*
                     Set<Tariff> tariffsSet = new HashSet<>();
                     List<UUID> tariffsUUIDs = packageDto.getTariffsUUIDs();
                     for (UUID tariffsUUID : tariffsUUIDs) {
                        Optional<Tariff> byId = tariffRepository.findById(tariffsUUID);
                        if (!byId.isPresent()) return new ApiResponse("Tariff not found", false);
                        tariffsSet.add(byId.get());
                    }
              */
      /*
                  List<UUID> simCardIDs = packageDto.getSimCardIDs();
                  Set<SimCard> simCards = new HashSet<>();

                  for (UUID simCardID : simCardIDs)
                  {
                      Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardID);
                      if (!optionalSimCard.isPresent()){ return new ApiResponse("SimCard not found", false);}
                      else { simCards.add(optionalSimCard.get()); }
                  }

                  Optional<SimCard> optionalSimCard = simCardRepository.findById(packageDto.getSimCardID());
                  if (!optionalSimCard.isPresent()) return new ApiResponse("Tariff not found", false);

              Set<Tariff> tariffsSet = new HashSet<>();
              List<UUID> tariffsUUIDs = packageDto.getTariffsUUIDs();
                  for (UUID tariffsUUID : tariffsUUIDs) {
                      Optional<Tariff> byId = tariffRepository.findById(tariffsUUID);
                      if (!byId.isPresent()) return new ApiResponse("Tariff not found", false);
                      tariffsSet.add(byId.get());
                  }
                      //  aPackage.setSimCards(simCards);
      */
       /*         balanceCompany=balanceCompany+(tariff.getPrice()+tariff.getTransitionPrice());
            companyBudget.setBalance(balanceCompany);
            companyBudget.setIncomeType(UssdCodsName.ACTIVATE_TARIFF.name());
            companyBudget.setLocalDate(LocalDate.now());
            budgetRepository.save(companyBudget);
*/
      /* Optional<SimCard> byId = simCardRepository.findByTariffId(tariffDto.getSimCardID());
      if (!byId.isPresent()) {
          return new ApiResponse("SimCard not found", false);
      }
      tariff.setSimCard(byId.get());
     /
     /* if (tariffDto.getSimCards()!=null) {
                Set<UUID> dtoSimCards = tariffDto.getSimCards();
                Set<SimCard> simCards = new HashSet<>();
                for (UUID simCard : dtoSimCards) {
                    Optional<SimCard> byId = simCardRepository.findById(simCard);
                    if (!byId.isPresent()) {
                        return new ApiResponse("SimCard not found", false);
                    }
                    simCards.add(byId.get());
                }
                tariff.setSimCards(simCards);
            }*/
     /*  Optional<SimCard> byId = simCardRepository.findByTariffId(tariffDto.getSimCardID());
            if (!byId.isPresent()) {
                return new ApiResponse("SimCard not found", false);
            }
            tariff.setSimCard(byId.get());
     /
     /*if (tariffDto.getSimCards()!=null) {
                Set<UUID> dtoSimCards = tariffDto.getSimCards();
                Set<SimCard> simCards = new HashSet<>();
                for (UUID simCard : dtoSimCards) {
                    Optional<SimCard> byId = simCardRepository.findById(simCard);
                    if (!byId.isPresent()) {
                        return new ApiResponse("SimCard not found", false);
                    }
                    simCards.add(byId.get());
                }
                  tariff.setSimCards(simCards);
            }*/
     /*
     ompaniya budjetiga hali tushmaydi simacarddagi pulni ishlatsa tushadi
     udgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.FILL_BALANCE.name(),
     ctionDto.getAmountToFillBalance(),LocalDate.now()));
     /
     *
        Optional<Tariff> bySimCardsId = tariffRepository.findBySimCardsId(simCardCall.getId());
        return bySimCardsId.map(value -> new ApiResponse("Tariff", true, value)).orElseGet(() ->
        new ApiResponse("Tariff not found", false));
     /
     /*
     ompaniya budjetiga hali tushmaydi simacarddagi pulni ishlatsa tushadi
     udgetService.addCompanyBudget(new CompanyBudgetDto(UssdCodsName.FILL_BALANCE.name(),
     ctionDto.getAmountToFillBalance(),LocalDate.now()));
     /
     *
        Optional<Tariff> bySimCardsId = tariffRepository.findBySimCardsId(simCardCall.getId());
        return bySimCardsId.map(value -> new ApiResponse("Tariff", true, value)).orElseGet(() ->
        new ApiResponse("Tariff not found", false));
     /




  /**/