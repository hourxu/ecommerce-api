package ecommerce.project.dto.product;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.dto.category.CategoryResponse;
import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.entity.Image;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        List<ImageResponse> imageResponse,
        String name,
        BigDecimal price,
        String description,
        CategoryResponse category,
        List<InventoryResponse> inventoryResponse
) {

}
