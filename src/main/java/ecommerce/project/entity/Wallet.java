package ecommerce.project.entity;

import ecommerce.project.dto.walletDeposit.WalletDepositResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "walls")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User  user;
    @Column(nullable = false)
    private BigDecimal balance=BigDecimal.ZERO;

    @OneToMany(mappedBy = "wallet")
    private List<WalletDeposit>walletDeposits=new ArrayList<>();
}
