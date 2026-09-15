package ecommerce.project.service;

import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.dto.payment.RequestMethodPayment;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(RequestMethodPayment requestMethod);
    List<OrderResponse>getall();
    OrderResponse deleted(UUID id);
    List<OrderResponse>history();
}
