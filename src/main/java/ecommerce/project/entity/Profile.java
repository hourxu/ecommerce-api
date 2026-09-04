package ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String gender;
    private String telPhone;
    private String address;
    private String profileImage;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private Image image;

}
