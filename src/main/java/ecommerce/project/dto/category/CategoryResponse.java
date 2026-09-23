package ecommerce.project.dto.category;

import ecommerce.project.entity.Image;
import ecommerce.project.entity.enums.Gender;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        Gender gender,
        String imageUrl,
        String description
) {

}
