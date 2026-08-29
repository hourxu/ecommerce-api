package ecommerce.project.dto.user;

public record AuthenticationResponse(
        String accessToken,
        long expiresIn
) {
}
