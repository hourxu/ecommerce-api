package ecommerce.project.mapper;

import ecommerce.project.dto.ImageResponse;
import ecommerce.project.dto.inventory.InventoryResponse;
import ecommerce.project.dto.product.ProductRequest;
import ecommerce.project.dto.product.ProductResponse;
import ecommerce.project.entity.Category;
import ecommerce.project.entity.Inventory;
import ecommerce.project.entity.Product;
import ecommerce.project.respositity.InventoryRepository;
import ecommerce.project.respositity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private final InventoryMapper inventoryMapper;
    public Product toProduct(ProductRequest request,Category category){
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(category)
                .build();
    }
    public ProductResponse toProductResponse(Product product){
        List<InventoryResponse>inventoryResponses=product.getInventory()==null?List.of()
                :product.getInventory().stream().map(inventoryMapper::toInventoryResponse)
                .toList();
        List<ImageResponse>imageResponses=product.getImages()==null?List.of()
                :product.getImages().stream().map(image ->new ImageResponse(
                        image.getId(),
                        image.getImageUrl()
        )).toList();
            return new ProductResponse(
                product.getId(),
                imageResponses,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                categoryMapper.toCategoryResponse(product.getCategory()),
                inventoryResponses
        );
    }
}
