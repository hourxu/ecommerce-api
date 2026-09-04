package ecommerce.project.dto.profile;

public record ProfileRequest(
        String firstName,
        String lastName,
        String telPhone,
        String gender
) {
}
