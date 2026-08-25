package ecommerce.project.dto.product;

import ecommerce.project.dto.inventory.InventoryResponse;

import java.util.List;

public record ProductDetail(
        List<InventoryResponse> inventoryResponse
) {
}
