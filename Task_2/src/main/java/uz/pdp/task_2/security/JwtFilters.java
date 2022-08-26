package uz.pdp.task_2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.task_2.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//CONTROLLERDAN OLDIN REQUEST HAR DOI  SHU FILTERGA KELADI:
@Component
public class JwtFilters extends OncePerRequestFilter {

    @Autowired
    JwtProvider   jwtProvider;

    @Autowired
    AuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                             FilterChain filterChain) throws ServletException, IOException {

        // HttpServletRequest dan token ni olish:
        // POSTMANN dagi Headers ning Key si: Authorization va value si uzun token shuni olamiz
        String token = request.getHeader("Authorization");

        // authorization mavjud bölsa va Bearer b-n boshlansa
        if (token!=null && token.startsWith("Bearer")){

        //AYNAN TOKENNI ÖZINI QIRQIB OLDIK
        token=token.substring(7);

     /*   // TOKENNI VALIDATSIYADAN ÖTKAZDIK(TOKEN BUZILMAGANLIGINI MUDDATI ÖTMAGANLIGINI)
        boolean validateToken = jwtProvider.validateToken(token);
        if (validateToken){

        // TOKENNI ICHIDAN USERNAME NI OLDI
        String username = jwtProvider.getUsernameFromToken(token);
         //LIST YOKI BASADAN QIDIRAMIZ, USERNAME ORQALI USERDETEILSNI OLDIK
        UserDetails userDetails = myAuthService.loadUserByUsername(username);
*/

            String  emailFromToken = jwtProvider.getEmailFromToken(token);
            if (emailFromToken!=null){
                // DB DAN USERNAME NI TOPISH
           UserDetails userDetails = myAuthService.loadUserByUsername(emailFromToken);


      // USERDETAILS ORQALI AUTHENTICATION YARATIB OLDIK: BU CLASS 3 TALIK CONSTRUKTOR BUNDA SYSTEMAGA KIRDI DEB QABUL
      // QILADI VA Authentication qaytaradi.LOGIN PAROL BERSAK 2 TALIK KONSTRUCTORLI CLASS ISHLATGAN BÖLARDIK.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // getContext() ning Authentication nida Hozircha null turipti
        SecurityContextHolder.getContext().getAuthentication();

        // SYSTEMA ( SecurityContextHolder ) GA KIM KIRGANLIGINI ÖRNATDIK. ENDI null BÖLMAYDI
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

       }
    }

         //FILTER/NI ISHALTISH BUYRUGI.ENDI CONTROLLER CLASSIDAGI YÖLGA SÖROV BORADI
         filterChain.doFilter(request,response);

    }

}
