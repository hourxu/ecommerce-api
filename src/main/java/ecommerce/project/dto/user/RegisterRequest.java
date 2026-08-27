package ecommerce.project.dto.user;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
