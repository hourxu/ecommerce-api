package ecommerce.project.dto.category;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description
) {

}
