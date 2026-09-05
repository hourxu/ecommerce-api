package ecommerce.project.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
            UUID cartId,
            UUID productId,
            String productName,
            BigDecimal price,
            UUID inventoryId,
            String size,
            Integer quantity
    ){

    }