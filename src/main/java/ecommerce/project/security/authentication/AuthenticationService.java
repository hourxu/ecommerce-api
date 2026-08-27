package ecommerce.project.security.authentication;

import ecommerce.project.dto.user.LoginRequest;
import ecommerce.project.dto.user.AuthenticationResponse;
import ecommerce.project.dto.user.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}
