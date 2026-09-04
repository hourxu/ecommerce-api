package ecommerce.project.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        BigDecimal totalPrice,
        LocalDateTime createAt,
        List<OrderItemResponse> orderItemResponses
){

}
