package ecommerce.project.dto.profile;

import java.util.UUID;

public record ProfileResponse(
        UUID id,
        String profileImage,
        String firstName,
        String lastName,
        String telPhone,
        String gender
) {
}
