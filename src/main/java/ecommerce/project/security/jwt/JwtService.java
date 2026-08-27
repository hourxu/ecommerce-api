package ecommerce.project.security.jwt;

import ecommerce.project.dto.user.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(
                jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(
            UUID userid,
            String gmail,
            Role role
    ){
        return Jwts.builder()
                .subject(userid.toString())
                .claim("email",gmail)
                .claim("authorities", List.of("ROLE_"+role.name()))
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+jwtProperties.expireSeconds()*1000)
                        )
                .signWith(getKey())
                .compact();
    }
    public String getEmail(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email",String.class);
    }
    public List<String> getAuthorities(String token){
        List authorities= Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("authorities", List.class)
                ;
        return authorities;
    }
    public UUID userid(String token ){
        String subject=Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return UUID.fromString(subject);
    }
}