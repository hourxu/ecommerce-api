package ecommerce.project.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse (
        UUID id,
        UUID inventoryId,
        String productName,
        String size,
        BigDecimal price,
        Integer quantity
){
}
