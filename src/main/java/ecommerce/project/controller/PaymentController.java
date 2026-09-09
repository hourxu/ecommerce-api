package ecommerce.project.controller;

import ecommerce.project.dto.payment.PaymentResponse;
import ecommerce.project.service.PaymentService;
import ecommerce.project.service.impl.paymentGetway.OrderPaymentService;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderPaymentService orderPaymentService;

    @PostMapping("{orderId}")
    public ResponseEntity<PaymentResponse> createQR(@PathVariable UUID orderId){
        return ResponseEntity.ok(paymentService.createPayment(orderId));
    }
    @PostMapping("/ImageQR")
    public ResponseEntity<byte[]>generate(@RequestBody KHQRData khqrData) throws Exception{
            byte[] image= orderPaymentService.generateQr(khqrData.getQr());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }
}
