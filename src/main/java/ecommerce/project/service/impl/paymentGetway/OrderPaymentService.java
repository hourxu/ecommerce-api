package ecommerce.project.service.impl.paymentGetway;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import ecommerce.project.config.ABAConfig;
import io.github.tongbora.bakong.dto.BakongRequest;
import io.github.tongbora.bakong.dto.BakongResponse;
import io.github.tongbora.bakong.dto.CheckTransactionRequest;
import io.github.tongbora.bakong.service.BakongService;
import kh.gov.nbc.bakong_khqr.model.KHQRCurrency;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class OrderPaymentService {

    private final BakongService bakongService;
    private final RestClient restClientBaKong;
    private final RestClient restClientAba;
    private final ABAConfig abAconfig;
    @Value("${bakong.account-id}")
    private String bakongAccountId;

    public OrderPaymentService(
            BakongService bakongService,

            @Qualifier("restClientBaKong")
            RestClient restClientBakong,

            @Qualifier("restClientaba")
            RestClient restClientAba, ABAConfig abAconfig
    ) {
        this.bakongService = bakongService;
        this.restClientBaKong = restClientBakong;
        this.restClientAba = restClientAba;
        this.abAconfig = abAconfig;
    }

    public KHQRResponse<KHQRData> generateQrForOrder(
            UUID orderId,
            BigDecimal amount
    ) {

        String shortId = orderId.toString();

        if (shortId.length() > 8) {
            shortId = shortId.substring(0, 8);
        }

        BakongRequest request = new BakongRequest(
                KHQRCurrency.USD,
                amount.doubleValue(),
                "Ty BunHour",
                "PHNOM PENH",
                bakongAccountId,
                "ABA",
                null,
                15,
                shortId,
                "STORE",
                "TERMINAL1",
                null,
                "Payment",
                "km",
                "Ty BunHour",
                "ភ្នំពេញ"
        );

        KHQRResponse<KHQRData> response =
                bakongService.generateQR(request);

        if (response.getKHQRStatus().getCode() != 0
                || response.getData() == null) {

            throw new RuntimeException(
                    "QR fail: " +
                            response.getKHQRStatus().getMessage()
            );
        }

        return response;
    }

    public boolean isOrderPaid(String md5) {

        BakongResponse response =
                bakongService.checkTransactionByMD5(
                        new CheckTransactionRequest(md5)
                );

        return response.responseCode() == 0;
    }

    public byte[] generateQr(String qrText) throws Exception {
        Map<EncodeHintType, Object> hint = new HashMap<>();
        hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hint.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new MultiFormatWriter().encode(
                qrText,
                BarcodeFormat.QR_CODE,
                200,
                200,
                hint
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                outputStream
        );
        return outputStream.toByteArray();
    }

    public String generateDeeplink(String khqrString) {
        try{
            Map response = restClientBaKong
                    .post()
                    .uri("/v1/generate_deeplink_by_qr")
                    .body(
                            Map.of(
                                    "qr",khqrString,
                                    "sourceInfo",Map.of(
                                            "appName", "Ecommerce",
                                            "appIconUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlaVwQm01dDivG4jVlhgqzsysZ_Ck8VVeu3Ntv8nx3l4YSh93MvnOdn085&s=10"

                                    )

                            )
                    )
                    .retrieve()
                    .body(Map.class);
            if(response != null && Integer.valueOf(0).equals(response.get("responseCode"))){
                Map data = (Map) response.get("data");
                return data != null ? (String) data.get("shortLink"):null;
            }
        }catch(Exception e){
            log.error("Deeplink generation failed (non-fetal):",e);
        }

        return null;
    }

    public String generateABADeeplink(BigDecimal amount) {

        try {

            String reqTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String tranId = "ORD" + System.currentTimeMillis();
            String merchantId = abAconfig.getMerchantId();
            String firstName = "Ty";
            String lastName = "BunHour";
            String email = "test@gmail.com";
            String phone = "012345678";
            String purchaseType = "purchase";
            String paymentOption = "abapay_khqr";
            String currency = "USD";
            String qrImageTemplate = "template3_color";
            BigDecimal amountValue = amount.setScale(2);
            String hash = generateAbaQrHash(reqTime, merchantId, tranId, amountValue,  firstName, lastName, email, phone, purchaseType, paymentOption, currency,qrImageTemplate
            );
            Map<String, Object> request = new LinkedHashMap<>();

            request.put("req_time", reqTime);
            request.put("merchant_id",abAconfig.getMerchantId());
            request.put("tran_id", tranId);
            request.put("first_name", firstName);
            request.put("last_name", lastName);
            request.put("email", email);
            request.put("phone", phone);
            request.put("amount", amountValue);
            request.put("purchase_type", purchaseType);
            request.put("payment_option", paymentOption);
            request.put("currency", currency);
            request.put("qr_image_template", qrImageTemplate);
            request.put("hash", hash);
            log.info("ABA request: {}", request);

            Map response = restClientAba
                    .post()
                    .uri("/api/payment-gateway/v1/payments/generate-qr")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(Map.class);
            log.info("ABA response: {}", response);
            if (response == null) {
                throw new RuntimeException("ABA response is null");
            }

            Map<String, Object> status =
                    (Map<String, Object>) response.get("status");

            if (status == null ||
                    !"0".equals(String.valueOf(status.get("code")))) {

                throw new RuntimeException(
                        "ABA payment failed: " + response
                );
            }
            String deeplink =
                    (String) response.get("abapay_deeplink");
            if (deeplink == null || deeplink.isBlank()) {
                throw new RuntimeException(
                        "ABA deeplink was not returned"
                );
            }
            return deeplink;

        } catch (Exception e) {

            throw new RuntimeException(
                    "ABA payment failed",
                    e
            );
        }
    }

    private String generateAbaQrHash(
            String reqTime,
            String merchantId,
            String tranId,
            BigDecimal amount,
            String firstName,
            String lastName,
            String email,
            String phone,
            String purchaseType,
            String paymentOption,
            String currency,

            String qrImageTemplate
    ) {

        String amountValue = amount.setScale(2).toPlainString();

        String data =
                value(reqTime)
                        + value(merchantId)
                        + value(tranId)
                        + amountValue
                        + value(firstName)
                        + value(lastName)
                        + value(email)
                        + value(phone)
                        + value(purchaseType)
                        + value(paymentOption)
                        + value(currency)
                        + value(qrImageTemplate);

        log.info("ABA hash data: {}", data);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");

            SecretKeySpec secretKey = new SecretKeySpec(
                    abAconfig.getApiKey().getBytes(StandardCharsets.UTF_8),
                    "HmacSHA512"
            );

            mac.init(secretKey);

            byte[] hmac = mac.doFinal(
                    data.getBytes(StandardCharsets.UTF_8)
            );

            return Base64.getEncoder().encodeToString(hmac);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ABA hash", e);
        }
    }

    private String value(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}