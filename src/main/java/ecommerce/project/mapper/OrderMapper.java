package ecommerce.project.mapper;

import ecommerce.project.dto.order.OrderItemResponse;
import ecommerce.project.dto.order.OrderResponse;
import ecommerce.project.entity.CartItem;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
   public OrderItem toEntity(CartItem cartItem,Order order){
//       OrderItem orderItem= new OrderItem();
//       orderItem.setOrder(order);
//       orderItem.setInventory(cartItem.getInventory());
//       orderItem.setPrice(cartItem.getInventory().getProduct().getPrice());
//       orderItem.setQuantity(cartItem.getQuantity());
       return OrderItem.builder()
               .order(order)
               .inventory(cartItem.getInventory())
               .price(cartItem.getInventory().getProduct().getPrice())
               .quantity(cartItem.getQuantity()).build();
   }
   public OrderItemResponse toResponse(OrderItem orderItem){
       return new OrderItemResponse(
               orderItem.getId(),
               orderItem.getInventory().getId(),
               orderItem.getInventory().getProduct().getName(),
               orderItem.getInventory().getSize(),
               orderItem.getPrice(),
               orderItem.getQuantity()
       );
   }
}
