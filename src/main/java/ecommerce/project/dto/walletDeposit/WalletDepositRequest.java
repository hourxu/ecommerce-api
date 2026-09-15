package ecommerce.project.dto.walletDeposit;

import java.math.BigDecimal;

public record WalletDepositRequest(
        BigDecimal amount
) {

}
