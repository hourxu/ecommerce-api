package ecommerce.project.Security;

import ecommerce.project.dto.User.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class Jwt {

    private final String secretKey = "fjaskhdfalskfhlaksfhlaksfa123456";

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(
            UUID userid,
            String gmail,
            Role role
    ){
        return Jwts.builder()
                .subject(userid.toString())
                .claim("gmail",gmail)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+864400000)
                )
                .signWith(getKey())
                .compact();
    }
    public String getGmail(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("gmail",String.class);
    }
    public Role getRole(String token){
        String role= Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class)
                ;
        return Role.valueOf(role);
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