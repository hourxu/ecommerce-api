package ecommerce.project.dto.User;

public record LoginResponse(
        String token,
        Role role
) {
}
