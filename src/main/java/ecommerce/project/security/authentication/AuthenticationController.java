package ecommerce.project.security.authentication;

import ecommerce.project.dto.user.LoginRequest;
import ecommerce.project.dto.user.AuthenticationResponse;
import ecommerce.project.dto.user.RefreshTokenRequest;
import ecommerce.project.dto.user.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@RequestBody RegisterRequest request){

       return userService.register(request);

    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request){
       return  userService.login(request);
    }
    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest request){
        return userService.refreshToken(request);
    }
}
