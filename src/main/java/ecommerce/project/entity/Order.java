package ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal totalPrice;
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem>orderItems=new ArrayList<>();
}
