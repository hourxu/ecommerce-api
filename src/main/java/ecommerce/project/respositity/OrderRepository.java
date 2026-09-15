package ecommerce.project.respositity;

import ecommerce.project.entity.Order;
import ecommerce.project.entity.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order>findByUser_IdAndStatusOrderByCreateAtDesc(UUID userId, OrderStatus status);
}
