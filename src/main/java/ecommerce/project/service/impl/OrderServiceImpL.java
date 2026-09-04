package ecommerce.project.service.impl;

import ecommerce.project.dto.order.OrderItemResponse;
import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.entity.*;
import ecommerce.project.entity.enums.CartStatus;
import ecommerce.project.exception.CartNotFoundException;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.mapper.OrderMapper;
import ecommerce.project.respositity.CartRepository;
import ecommerce.project.respositity.OrderRepository;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpL implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderResponse createOrder() {
        User   user=extractUser();//check who are you
        Cart cart=cartRepository.findFirstByUser_IdAndStatus(
                user.getId(),
                CartStatus.ACTIVE
        ).orElseThrow(CartNotFoundException::new);//first add to cart
        //create order
        Order order= new Order();
        order.setUser(user);//store user who?
        order.setCreateAt(LocalDateTime.now());
        BigDecimal total=BigDecimal.ZERO;

        //======convert cartItem to orderItem
        for(CartItem cartItem: cart.getCartItems()){//loop one by one of cart finding cartItem
            OrderItem orderItem=orderMapper.toEntity(cartItem,order);//convert mapper to entity
            order.getOrderItems().add(orderItem);//put everything into cartItem

            BigDecimal itemTotal=orderItem.getPrice().multiply(
                    BigDecimal.valueOf(orderItem.getQuantity())
            );
            total=total.add(itemTotal);
        }
        order.setTotalPrice(total);

        orderRepository.save(order);
        //====convert orderItem to orderItemResponse
        List<OrderItemResponse> items = order.getOrderItems().stream().map(
                orderMapper::toResponse
        ).toList();

        // Return response
        return new OrderResponse(
                order.getId(),
                order.getTotalPrice(),
                order.getCreateAt(),
                items
        );
    }

    @Override
    public List<OrderResponse> getall() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {//gave data to orderItem
                    List<OrderItemResponse> items =
                            order.getOrderItems()
                                    .stream()
                                    .map(orderMapper::toResponse).toList();

                    return new OrderResponse(
                            order.getId(),
                            order.getTotalPrice(),
                            order.getCreateAt(),
                            items
                    );
                })
                .toList();
    }

    private User extractUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
