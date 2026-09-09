package ecommerce.project.service;

import ecommerce.project.dto.payment.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse createPayment(UUID orderId);
}
