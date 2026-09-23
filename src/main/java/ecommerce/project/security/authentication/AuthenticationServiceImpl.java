package ecommerce.project.security.authentication;

import ecommerce.project.dto.user.RefreshTokenRequest;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.security.jwt.JwtService;
import ecommerce.project.dto.user.LoginRequest;
import ecommerce.project.dto.user.AuthenticationResponse;
import ecommerce.project.dto.user.RegisterRequest;
import ecommerce.project.entity.enums.Role;
import ecommerce.project.entity.User;
import ecommerce.project.exception.UserAlreadyExistException;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistException();
        }
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
        String refreshToken= jwtService.generateRefreshToken(user.getId());
        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                jwtProperties.accessExpireSeconds()
        );
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new UserNotFoundException();
        }

        String AccessToken = jwtService.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
            String refreshToken= jwtService.generateRefreshToken(user.getId());
        return new AuthenticationResponse(
                AccessToken,
                refreshToken,
                jwtProperties.accessExpireSeconds()

        );
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken=request.refreshToken();
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }
        UUID userId=jwtService.userid(refreshToken);
        User user=userRepository.findByid(userId).orElseThrow(UserNotFoundException::new);
        String newAccessToken= jwtService.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
        return new AuthenticationResponse(
                newAccessToken,
                refreshToken,
                jwtProperties.accessExpireSeconds()
        );
    }

}