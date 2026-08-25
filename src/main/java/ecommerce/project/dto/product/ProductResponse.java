package ecommerce.project.dto.product;

import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.inventory.InventoryResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price,
        String description,
        CategoryResponse category,
        List<InventoryResponse> inventoryResponse
) {

}
