package ecommerce.project.dto.inventory;

import java.util.UUID;

public record InventoryRequest(
        String size,
        Integer quantity,
        UUID productId
) {

}
