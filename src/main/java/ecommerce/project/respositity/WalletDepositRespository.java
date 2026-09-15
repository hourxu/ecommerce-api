package ecommerce.project.respositity;

import ecommerce.project.entity.WalletDeposit;
import ecommerce.project.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WalletDepositRespository
extends JpaRepository<WalletDeposit, UUID>
{
    List<WalletDeposit>findByStatus(PaymentStatus paymentStatus);
}
