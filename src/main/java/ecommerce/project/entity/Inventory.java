package ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "inventories",uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"product_id", "size"}
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String size;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
