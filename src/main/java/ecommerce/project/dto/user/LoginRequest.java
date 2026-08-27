package ecommerce.project.dto.user;

public record LoginRequest(
        String email,
        String password
) {
}
