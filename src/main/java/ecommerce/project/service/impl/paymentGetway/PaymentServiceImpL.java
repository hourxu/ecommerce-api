package ecommerce.project.service.impl.paymentGetway;

import ecommerce.project.dto.payment.PaymentResponse;
import ecommerce.project.entity.Order;
import ecommerce.project.entity.Payment;
import ecommerce.project.entity.enums.OrderStatus;
import ecommerce.project.entity.enums.PaymentStatus;
import ecommerce.project.exception.OrderNotFoundException;
import ecommerce.project.respositity.OrderRepository;
import ecommerce.project.respositity.PaymentRepository;
import ecommerce.project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.tongbora.bakong.service.BakongService;
import io.github.tongbora.bakong.dto.BakongRequest;

import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import kh.gov.nbc.bakong_khqr.model.KHQRCurrency;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpL implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderPaymentService orderPaymentService;
    @Override
    public PaymentResponse createPayment(UUID orderId) {
        Order order =orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        if(order.getStatus()!= OrderStatus.PENDING){
            throw new RuntimeException("order is waiting ");
        }

        Optional<Payment>existing=paymentRepository.findByOrderId(orderId);
        if(existing.isPresent()){
            Payment payment=existing.get();
            if(payment.getPaymentStatus()==PaymentStatus.PENDING){
                return toResponse(payment);
            }
            if(PaymentStatus.PAID.equals(payment.getPaymentStatus())) throw  new RuntimeException("Payment already");
        }
        KHQRResponse<KHQRData>response=orderPaymentService.generateQrForOrder(orderId,order.getTotalPrice());

        String deeplink = orderPaymentService.generateDeeplink(response.getData().getQr());
        log.info("deep link {}", deeplink);

        KHQRData khqrData= response.getData();
        Payment payment=new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setTransactionId(khqrData.getMd5());
        payment.setQrCode(khqrData.getQr());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setCreateAt(LocalDateTime.now());
        payment.setMD5(khqrData.getMd5());

        paymentRepository.save(payment);
        return new PaymentResponse(
                payment.getId(),
                order.getId(),
                payment.getAmount(),
                payment.getMD5(),
                payment.getQrCode(),
                payment.getPaymentStatus()
        );

    }
    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getMD5(),
                payment.getQrCode(),
                payment.getPaymentStatus()
        );
    }
}
