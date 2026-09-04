package ecommerce.project.dto;

import java.util.UUID;

public record ImageResponse(
        UUID id ,
        String imageUrl
) {
}
