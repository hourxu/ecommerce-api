package ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
