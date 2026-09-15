package ecommerce.project.respositity;

import ecommerce.project.entity.WalletDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<WalletDeposit, UUID> {
}
