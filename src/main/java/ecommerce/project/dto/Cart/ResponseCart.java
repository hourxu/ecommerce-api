package ecommerce.project.dto.Cart;

import java.util.List;
import java.util.UUID;

public record ResponseCart(
        UUID id,
        List<CartitemResponse> products
) {

}
