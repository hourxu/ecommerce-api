package ecommerce.project.entity;

import ecommerce.project.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private Cart cart;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Order>orders=new ArrayList<>();

    @OneToOne
    private Profile profile;
}
