package ecommerce.project.dto.walletDeposit;

import ecommerce.project.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDepositResponse(
        UUID depositId,
        BigDecimal amount,
        String qrCode,
        String deeplink,
        String deeplinkaba,
        PaymentStatus status
) {
}
