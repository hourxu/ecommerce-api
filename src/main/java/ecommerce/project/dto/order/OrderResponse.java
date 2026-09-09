package ecommerce.project.dto.order;

import ecommerce.project.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        LocalDateTime createAt,
        List<OrderItemResponse> orderItemResponses
){

}
