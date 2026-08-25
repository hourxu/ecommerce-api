package ecommerce.project.service;

import ecommerce.project.dto.User.LoginRequest;
import ecommerce.project.dto.User.LoginResponse;
import ecommerce.project.dto.User.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void registerAdmin(RegisterRequest request);
}
