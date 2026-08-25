package ecommerce.project.service.impl;

import ecommerce.project.Security.Jwt;
import ecommerce.project.dto.User.LoginRequest;
import ecommerce.project.dto.User.LoginResponse;
import ecommerce.project.dto.User.RegisterRequest;
import ecommerce.project.dto.User.Role;
import ecommerce.project.entity.User;
import ecommerce.project.exception.UserAlreadyExistException;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwt jwt;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByGmail(request.gmail())) {
            throw new UserAlreadyExistException();
        }

        User user = User.builder()
                .username(request.username())
                .gmail(request.gmail())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByGmail(request.gmail())
                .orElseThrow(() ->
                        new RuntimeException("Gmail not found")
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new RuntimeException("Password is incorrect");
        }

        String token = jwt.generateToken(
                user.getId(),
                user.getGmail(),
                user.getRole()
        );

        return new LoginResponse(token,user.getRole());
    }

    @Override
    public void registerAdmin(RegisterRequest request) {
        if(userRepository.existsByGmail(request.gmail())){
            throw new UserAlreadyExistException();
        }
        User admin = User.builder()
                .username(request.username())
                .gmail(request.gmail())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
    }
}