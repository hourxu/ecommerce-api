package ecommerce.project.service.impl;

import ecommerce.project.dto.order.OrderItemResponse;
import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.dto.payment.PaymentResponse;
import ecommerce.project.dto.payment.RequestMethodPayment;
import ecommerce.project.entity.*;
import ecommerce.project.entity.enums.CartStatus;
import ecommerce.project.entity.enums.OrderStatus;
import ecommerce.project.entity.enums.PaymentMethod;
import ecommerce.project.exception.*;
import ecommerce.project.mapper.OrderMapper;
import ecommerce.project.respositity.CartRepository;
import ecommerce.project.respositity.OrderRepository;
import ecommerce.project.respositity.PaymentRepository;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.service.OrderService;
import ecommerce.project.service.PaymentService;
import ecommerce.project.service.WalletDepositService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpL implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final WalletDepositService walletDepositService;
    @Override
    @Transactional
    public OrderResponse createOrder(RequestMethodPayment requestMethodPayment) {
        User   user=extractUser();//check who are you
        Cart cart=cartRepository.findFirstByUser_IdAndStatus(
                user.getId(),
                CartStatus.ACTIVE
        ).orElseThrow(CartNotFoundException::new);//first add to cart
        //create order


        Order order= new Order();
        order.setUser(user);//store user who?
        order.setCreateAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
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

        Order savedOrder =  orderRepository.save(order);
        cart.setStatus(CartStatus.CHECKED_OUT);
        PaymentResponse payment = null;
        if(requestMethodPayment.paymentMethod() == PaymentMethod.WALLET){
            walletDepositService.decreaseBalance(order.getId());
        }else {
             payment = paymentService.createPayment(savedOrder.getId());
        }
        //====convert orderItem to orderItemResponse
        List<OrderItemResponse> items = order.getOrderItems().stream().map(
                orderMapper::toResponse
        ).toList();


        // Return response
        return new OrderResponse(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreateAt(),
                items,
                payment
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
                    PaymentResponse payment= paymentRepository.findByOrderId(order.getId())
                                                                .map(this::toResponse).orElse(null);
                    return new OrderResponse(
                            order.getId(),
                            order.getTotalPrice(),
                            order.getStatus(),
                            order.getCreateAt(),
                            items,
                            payment);
                })
                .toList();
    }

    @Override
    public OrderResponse deleted(UUID id) {
        Order order=orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderRepository.deleteById(id);
        throw new DeleteSuccessException();
    }

    @Override
    public List<OrderResponse> history() {
      User user=extractUser();
      List<Order>orders=orderRepository.findByUser_IdAndStatusOrderByCreateAtDesc(user.getId(),OrderStatus.PAID);
      return orders.stream().map(
              item->{
                  List<OrderItemResponse>orderItemResponses=item.getOrderItems().stream().map(
                          orderMapper::toResponse
                  ).toList();

                  return new OrderResponse(
                          item.getId(),
                          item.getTotalPrice(),
                          item.getStatus(),
                          item.getCreateAt(),
                          orderItemResponses,
                          null
                  );
              }
      ).toList();
    }


    private User extractUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
    private PaymentResponse toResponse(Payment payment){
        return new PaymentResponse(
                payment.getQrCode(),
                payment.getMD5(),
                payment.getPaymentStatus(),
                payment.getDeeplink(),
                payment.getDeeplinkaba());
    }
}
