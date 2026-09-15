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

import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import kh.gov.nbc.bakong_khqr.model.KHQRData;

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
    private final BakongService bakongService;
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
        KHQRData khqrData=response.getData();
        String khqr=khqrData.getQr();
        String deeplink = orderPaymentService.generateDeeplink(khqr);

        String deeplinkaba= orderPaymentService.generateABADeeplink(order.getTotalPrice());

        Payment payment=new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setTransactionId(khqrData.getMd5());
        payment.setQrCode(khqr);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setCreateAt(LocalDateTime.now());
        payment.setMD5(khqrData.getMd5());

        payment.setDeeplink(deeplink);
        payment.setDeeplinkaba(deeplinkaba);
        paymentRepository.save(payment);
        return new PaymentResponse(
                payment.getMD5(),
                payment.getQrCode(),
                payment.getPaymentStatus(),
                payment.getDeeplink(),
                payment.getDeeplinkaba()
        );

    }

    @Override
    public PaymentResponse checkPayment(UUID orderId) {
            Payment payment= paymentRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Payment not found"));

            if(payment.getPaymentStatus()==PaymentStatus.PAID){
                return toResponse(payment);
            }

            String md5=payment.getMD5();
            boolean paid=orderPaymentService.isOrderPaid(md5);
            if(paid){
                payment.setPaymentStatus(PaymentStatus.PAID);
                paymentRepository.save(payment);
                Order order=payment.getOrder();
                order.setStatus(OrderStatus.PAID);
                orderRepository.save(order);
            }
            return toResponse(payment);
    }


    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getMD5(),
                payment.getQrCode(),
                payment.getPaymentStatus(),
                payment.getDeeplink(),
                payment.getDeeplinkaba());
    }


}
