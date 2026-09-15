package ecommerce.project.controller;

import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.dto.payment.RequestMethodPayment;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.Payment;
import ecommerce.project.service.OrderService;
import ecommerce.project.service.PaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    //do not need requestBody because the cart has already requestBody
    public ResponseEntity<OrderResponse>createOrder(@RequestBody RequestMethodPayment requestMethodPayment){
        OrderResponse response=orderService.createOrder(requestMethodPayment);
        return ResponseEntity.ok(response);}
    @GetMapping
    public ResponseEntity<List<OrderResponse>>getall(){
        return ResponseEntity.ok(orderService.getall());
    }@DeleteMapping("{id}")
    public ResponseEntity<OrderResponse>deleted(@PathVariable UUID id){
        orderService.deleted(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>>history(){
       List<OrderResponse>orderResponses= orderService.history();
        return ResponseEntity.ok(orderResponses);
    }
}
