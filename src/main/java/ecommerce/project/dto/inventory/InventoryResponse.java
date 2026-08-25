package ecommerce.project.dto.inventory;

import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Product;

import java.util.UUID;

public record InventoryResponse(
        UUID id,
        String size,
        Integer quantity
) {
    }

