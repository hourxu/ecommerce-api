package ecommerce.project.controller;

import ecommerce.project.dto.User.LoginRequest;
import ecommerce.project.dto.User.LoginResponse;
import ecommerce.project.dto.User.RegisterRequest;
import ecommerce.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/admin")
    public ResponseEntity<Void>admin(@RequestBody RegisterRequest request){
        userService.registerAdmin(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return  new ResponseEntity<>(
                userService.login(request),
                HttpStatus.CREATED
        );
    }

}
