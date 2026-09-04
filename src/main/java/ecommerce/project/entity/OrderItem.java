package ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orderitems")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private Integer quantity;

    private BigDecimal price;
}
