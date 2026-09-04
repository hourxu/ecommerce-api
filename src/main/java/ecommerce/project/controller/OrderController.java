package ecommerce.project.controller;

import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse>createOrder(){
        orderService.createOrder();
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>>getall(){
        return ResponseEntity.ok(orderService.getall());
    }
}
