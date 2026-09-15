package ecommerce.project.respositity;

import ecommerce.project.entity.User;
import ecommerce.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet>findByUser_Id(UUID Id);
}
