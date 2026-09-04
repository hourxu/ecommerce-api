package ecommerce.project.service;

import ecommerce.project.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder();
    List<OrderResponse>getall();
}
