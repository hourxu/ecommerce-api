package ecommerce.project.dto.category;

import ecommerce.project.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "name is is mandatory")
        String name,
        Gender gender,
        String description
) {
}
