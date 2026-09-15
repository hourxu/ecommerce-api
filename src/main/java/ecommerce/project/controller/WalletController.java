package ecommerce.project.controller;

import ecommerce.project.dto.wallet.WalletResponse;
import ecommerce.project.dto.walletDeposit.WalletDepositRequest;
import ecommerce.project.dto.walletDeposit.WalletDepositResponse;
import ecommerce.project.service.WalletDepositService;
import ecommerce.project.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
@RestController
public class WalletController {
    private final WalletService walletService;
    private final WalletDepositService walletDepositService;
    @GetMapping
    public ResponseEntity<WalletResponse>getWallet(){
        return ResponseEntity.ok(walletService.getWallet());
    }
    @PostMapping
    public ResponseEntity<WalletDepositResponse>createDeposit(@RequestBody WalletDepositRequest request){
        return ResponseEntity.ok(walletDepositService.createDeposit(request));
    }
    @PostMapping("/{orderId}")
    public ResponseEntity<Void>decrease(@RequestBody UUID orderId){
        walletDepositService.decreaseBalance(orderId);
        return ResponseEntity.ok().build();
    }

}
