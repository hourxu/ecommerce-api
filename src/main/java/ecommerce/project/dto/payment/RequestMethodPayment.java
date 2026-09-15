package ecommerce.project.dto.payment;

import ecommerce.project.entity.enums.PaymentMethod;
import ecommerce.project.entity.enums.PaymentStatus;

public record RequestMethodPayment(
        PaymentMethod paymentMethod
) {

}
