package ecommerce.project.dto.Cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddCartItemRequest(
        @NotNull
        UUID inventoryId,
        @NotNull
        @Min(1)
        Integer quantity
) {
}
