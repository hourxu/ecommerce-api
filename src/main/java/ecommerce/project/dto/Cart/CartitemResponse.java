package ecommerce.project.dto.Cart;

import java.util.UUID;

public record CartitemResponse(
        UUID inventoryId,
        String size,
        Integer quantity
) {
}
