package ecommerce.project.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
/// =========define role of user and give information to Spring security
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,//get information user
            HttpServletResponse response,//send information
            FilterChain filterChain// continue request
    ) throws ServletException, IOException {
        //handle user
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtService.getEmail(token);//get email  of user
            List<String> role = jwtService.getAuthorities(token);//get role of user
            if(role==null || role.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(
                            role.getFirst()
                    );// tell role to spring security

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(authority)
                    );/// tell information to spring security

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);//spring security remember request

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}