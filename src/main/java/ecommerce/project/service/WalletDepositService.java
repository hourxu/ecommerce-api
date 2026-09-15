package ecommerce.project.service;

import ecommerce.project.dto.walletDeposit.WalletDepositRequest;
import ecommerce.project.dto.walletDeposit.WalletDepositResponse;
import ecommerce.project.entity.WalletDeposit;

import java.util.UUID;

public interface WalletDepositService {
    WalletDepositResponse createDeposit(WalletDepositRequest request);
    void checkDeposit();
    void  decreaseBalance(UUID orderId);
}
