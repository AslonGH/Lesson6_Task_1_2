package uz.pdp.task_2.service;/*
package uz.pdp.task_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.task_2.repository.CardRepository;
import uz.pdp.task_2.repository.EmployeeRepository;


//CLASSIMIZ TIPINI UserDetailsService TIPIGA AYLANTIRAMIZ
@Service
public class AuthCardService implements UserDetailsService {

                   // YAXSHISI CARTNING EGASINI  EMPLOYEEREPOSITORYGA JOYLA VA EMPLOYEEREPOSITORYDAN QIDIR !!!

    @Autowired
    CardRepository cardRepository;


     @Override
     public UserDetails loadUserByUsername(String number16) throws UsernameNotFoundException {
     return (UserDetails) cardRepository.findByNumber16(number16).orElseThrow(() -> new UsernameNotFoundException(number16 + "topilmadi"));
   }

}


 */
/*

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
    }*//*



*/
