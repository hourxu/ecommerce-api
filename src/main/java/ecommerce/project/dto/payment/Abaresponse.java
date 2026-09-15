package ecommerce.project.dto.payment;

public record Abaresponse(
        String qrString,
        String qrImage,
        String deeplink
) {
}
