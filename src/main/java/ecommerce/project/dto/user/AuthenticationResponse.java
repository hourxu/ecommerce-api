package ecommerce.project.dto.user;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken,
        long expires
) {}
