package ecommerce.project.dto.payment;

import ecommerce.project.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        String md5,
        String qrCode,
        PaymentStatus paymentStatus
) {
}
