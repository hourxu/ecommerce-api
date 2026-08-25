package ecommerce.project.dto.User;

public record RegisterRequest(
        String username,
        String gmail,
        String password
) {
}
