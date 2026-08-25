package ecommerce.project.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        String name,
        BigDecimal price,
        String  description,
        UUID categoryId
){


}
