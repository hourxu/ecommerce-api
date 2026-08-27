package ecommerce.project.entity;

import ecommerce.project.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Builder.Default
    private ProductStatus status = ProductStatus.ACTIVE;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Image>images;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Inventory>inventory=new ArrayList<>();

}

