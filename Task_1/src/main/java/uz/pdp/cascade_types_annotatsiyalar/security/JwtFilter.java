package uz.pdp.cascade_types_annotatsiyalar.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.cascade_types_annotatsiyalar.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

     @Autowired
     JwtProvider jwtProvider;

     @Autowired
     AuthService authService;


     // Token b-n Login qilganda, USERNI FILTERDAN ÖTKAZIB KEYIN SYSTEMAGA KIRITISH
     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                           FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization!=null&&authorization.startsWith("Bearer")){
            authorization=authorization.substring(7);
            String  emailFromToken = jwtProvider.getEmailFromToken(authorization);
            if (emailFromToken!=null){
                // DB DAN USERNAME NI TOPISH
                UserDetails userDetails = authService.loadUserByUsername(emailFromToken);

                // userDetails DAN OLGAN OBJECT NI SYSTEMAGA AUTHENTICATE QILISH KERAK:
                // userDetails-username, null- password; userDetails.getAuthorities() - Roles
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                 new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                //SYSTEMAGA USERNI KIRITISH
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         }
        }
         filterChain.doFilter(request,response);
    }


}
