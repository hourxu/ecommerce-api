package ecommerce.project.service;

import ecommerce.project.dto.order.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder();
    List<OrderResponse>getall();
    OrderResponse deleted(UUID id);
}
