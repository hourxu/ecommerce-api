package ecommerce.project.entity;

import ecommerce.project.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.apache.bcel.classfile.Code;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="deposits")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @ManyToOne
    @JoinColumn(name = "wallet-id")
    private Wallet wallet;

    private BigDecimal amount;

    private String md5;
    @Column(columnDefinition = "TEXT")
    private String qrCode;
    @Column(columnDefinition = "TEXT")
    private String deeplink;
    @Column(columnDefinition = "TEXT")
    private String deeplinkaba;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;
}
