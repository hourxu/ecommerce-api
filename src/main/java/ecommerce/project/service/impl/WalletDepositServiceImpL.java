package ecommerce.project.service.impl;

import ecommerce.project.dto.walletDeposit.WalletDepositRequest;
import ecommerce.project.dto.walletDeposit.WalletDepositResponse;
import ecommerce.project.entity.*;
import ecommerce.project.entity.enums.OrderStatus;
import ecommerce.project.entity.enums.PaymentStatus;
import ecommerce.project.exception.OrderNotFoundException;
import ecommerce.project.exception.UserNotFoundException;
import ecommerce.project.exception.WallNotFoundException;
import ecommerce.project.respositity.*;
import ecommerce.project.service.PaymentService;
import ecommerce.project.service.WalletDepositService;
import ecommerce.project.service.impl.paymentGetway.OrderPaymentService;
import jakarta.transaction.Transactional;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletDepositServiceImpL implements WalletDepositService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletDepositRespository walletDepositRespository;
    private final OrderPaymentService orderPaymentService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public WalletDepositResponse createDeposit(WalletDepositRequest request) {
        User user= current();
        Wallet wallet=walletRepository.findByUser_Id(user.getId()).orElseThrow(WallNotFoundException::new);

        if(request.amount()==null||request.amount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("amount should be greater that zero");
        }

        WalletDeposit deposit= new WalletDeposit();
        deposit.setWallet(wallet);
        deposit.setAmount(request.amount());
        deposit.setCreatedAt(LocalDateTime.now());
        deposit.setStatus(PaymentStatus.PENDING);
        walletDepositRespository.save(deposit);

        KHQRResponse<KHQRData>qrResponse=orderPaymentService.generateQrForOrder(
                deposit.getId(),
                deposit.getAmount()
        );
        String qrcode=qrResponse.getData().getQr();
        String md5= qrResponse.getData().getMd5();
        deposit.setQrCode(qrcode);
        deposit.setMd5(md5);

        String deeplink= orderPaymentService.generateDeeplink(qrcode);
        String abadeeplink= orderPaymentService.generateABADeeplink(deposit.getAmount());

        deposit.setDeeplink(deeplink);
        deposit.setDeeplinkaba(abadeeplink);

        walletDepositRespository.save(deposit);

        return new WalletDepositResponse(
                deposit.getId(),
                deposit.getAmount(),
                deposit.getQrCode(),
                deposit.getDeeplink(),
                deposit.getDeeplinkaba(),
                deposit.getStatus()
        );

    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void checkDeposit() {
        List<WalletDeposit>deposits=walletDepositRespository.findByStatus(PaymentStatus.PENDING);
        for(WalletDeposit deposit: deposits){
            try{
                boolean paid=orderPaymentService.isOrderPaid(
                        deposit.getMd5()
                );
                if(!paid){
                    continue;
                }
                Wallet wallet=deposit.getWallet();
                wallet.setBalance(wallet.getBalance().add(deposit.getAmount()));
                deposit.setStatus(PaymentStatus.PAID);
                walletRepository.save(wallet);
                walletDepositRespository.save(deposit);
            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    @Override
    @Transactional
    public void decreaseBalance(UUID orderId) {
        // orderid
        Order order=orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if(order.getStatus()== OrderStatus.PAID){
            throw new RuntimeException("order already");
        }
        User user= order.getUser();
        Wallet wallet=walletRepository.findByUser_Id(user.getId()).orElseThrow(WallNotFoundException::new);
        BigDecimal amount=order.getTotalPrice();

        if(wallet.getBalance().compareTo(amount)<=0){
            throw new RuntimeException("Balance not enough");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));//balance - amount

        order.setStatus(OrderStatus.PAID);
        Payment payment= new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setCreateAt(LocalDateTime.now());

        paymentRepository.save(payment);
        walletRepository.save(wallet);
        orderRepository.save(order);
    }

    private User current(){
        Authentication  authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
