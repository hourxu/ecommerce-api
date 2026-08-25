package ecommerce.project.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "name is is mandatory")
        String name,
        String description
) {
}
