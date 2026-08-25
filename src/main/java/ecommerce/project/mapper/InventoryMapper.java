package ecommerce.project.mapper;

import ecommerce.project.dto.inventory.InventoryRequest;
import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.entity.Inventory;
import ecommerce.project.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMapper {
    public Inventory  toInventory(InventoryRequest request, Product product){
        return Inventory.builder()
                .size(request.size())
                .quantity(request.quantity())
                .product(product)
                .build();
    }
    public InventoryResponse toInventoryResponse(Inventory inventory){
        return new InventoryResponse(
                inventory.getId(),
                inventory.getSize(),
                inventory.getQuantity()
        );
    }
}
