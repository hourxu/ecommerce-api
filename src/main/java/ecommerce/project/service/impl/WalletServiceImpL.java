package ecommerce.project.service.impl;

import ecommerce.project.dto.wallet.WalletResponse;
import ecommerce.project.entity.User;
import ecommerce.project.entity.Wallet;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.respositity.UserRepository;
import ecommerce.project.respositity.WalletRepository;
import ecommerce.project.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpL implements WalletService {
    private final WalletRepository  walletRepository;
    private final UserRepository userRepository;

    @Override
    public WalletResponse getWallet() {
        User user = extractUser();
        Wallet wallet = walletRepository.findByUser_Id(user.getId()).orElseGet(

                //orelseget =If the wallet exists, use it. If it doesn't exist, create one
                () -> {
                    Wallet Newwallet = new Wallet();
                    Newwallet.setUser(user);
                    Newwallet.setBalance(BigDecimal.ZERO);
                    return walletRepository.save(Newwallet);
                }
        );
        return new WalletResponse(
                wallet.getId(),
                wallet.getBalance()
        );

    }
    private User extractUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
