package ecommerce.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ABAConfig {

    @Value("${aba.merchant-id}")
    private String merchantId;

    @Value("${aba.api-key}")
    private String apiKey;

    public String getMerchantId() {
        return merchantId;
    }

    public String getApiKey() {
        return apiKey;
    }
}