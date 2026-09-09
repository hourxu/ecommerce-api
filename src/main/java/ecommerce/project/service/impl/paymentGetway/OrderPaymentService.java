package ecommerce.project.service.impl.paymentGetway;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.github.tongbora.bakong.dto.BakongRequest;
import io.github.tongbora.bakong.dto.BakongResponse;
import io.github.tongbora.bakong.dto.CheckTransactionRequest;
import io.github.tongbora.bakong.service.BakongService;
import kh.gov.nbc.bakong_khqr.model.KHQRCurrency;
import kh.gov.nbc.bakong_khqr.model.KHQRData;
import kh.gov.nbc.bakong_khqr.model.KHQRResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.channels.MulticastChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentService {

    private final BakongService bakongService;
    private final RestClient restClient;

    @Value("${bakong.account-id}")
    private String bakongAccountId;

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
    public byte[] generateQr(String qrText) throws Exception{
        Map<EncodeHintType, Object>hint=new HashMap<>();
        hint.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hint.put(EncodeHintType.MARGIN,1);
        BitMatrix matrix = new MultiFormatWriter().encode(
                qrText,
                BarcodeFormat.QR_CODE,
                200,
                200,
                hint
        );
        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                outputStream
        );
        return outputStream.toByteArray();
    }

public String generateDeeplink(String khqrString) {
    try{
        Map response = restClient
                .post()
                .uri("/v1/generate_deeplink_by_qr")
                .body(
                        Map.of(
                                "qr",khqrString,
                                "sourceInfo",Map.of(
                                        "appName", "Ecommerce",
                                        "appIconUrl", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlaVwQm01dDivG4jVlhgqzsysZ_Ck8VVeu3Ntv8nx3l4YSh93MvnOdn085&s=10",
                                        "appDeepLinkCallback", "https://cinemahub.com/payment/return"
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
        log.warn("Deeplink generation failed (non-fetal): {}",e.getMessage());
    }

    return null;
}
}