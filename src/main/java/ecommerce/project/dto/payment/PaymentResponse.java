package ecommerce.project.dto.payment;

import ecommerce.project.entity.enums.PaymentStatus;

public record PaymentResponse(
        String md5,
        String qrCode,
        PaymentStatus paymentStatus,
        String deeplink,
        String deeplinkaba) {
}
