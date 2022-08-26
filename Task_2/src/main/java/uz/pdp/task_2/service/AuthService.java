package uz.pdp.task_2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.repository.EmployeeRepository;


                             // CLASSIMIZ TIPINI UserDetailsService TIPIGA AYLANTIRAMIZ
@Service
public class AuthService implements UserDetailsService {



    @Autowired
    EmployeeRepository employeeRepository;


    // KIMDIR BROWSERDAN username BERSA TEPADAGI List dan qidiradi.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      return employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+"topilmadi"));

    }



}





 /*
//        UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
//        if (!userDetails.isEnabled())
//        return new Result("User topilmadi",false);

{

      boolean exists = cardRepository.existsByUsername(username);
      if (exists){

     List<User>userList=new ArrayList<>(
         Arrays.asList(
                        new User("Ali",     passwordEncoder.encode("ali123"),new ArrayList<>()),
                        new User("Vali",    passwordEncoder.encode("vali123"),new ArrayList<>()),
                        new User("Abror",   passwordEncoder.encode("abror123"),new ArrayList<>()),
                        new User("Alisher", passwordEncoder.encode("alisher123"),new ArrayList<>())
                      )
     );
        for (User user : userList) {
           if (user.getUsername().equals(username))
               return user;
           }
        throw new UsernameNotFoundException("User topilmadi");
    }*/
