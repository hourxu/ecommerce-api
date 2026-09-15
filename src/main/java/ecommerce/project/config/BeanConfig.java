package ecommerce.project.config;

import ecommerce.project.security.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class BeanConfig {

    @Value("${aba.base-url}")
    private String baseUrlAba;

    @Bean
    public RestClient restClientaba() {

        return RestClient.builder()
                .baseUrl(baseUrlAba)
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }
    @Bean
    public RestClient restClientBaKong(
            @Value("${bakong.base-url}") String baseUrlbakong,
            @Value("${bakong.token-Bakong}") String bakongToken) {

        if (baseUrlbakong == null || baseUrlbakong.isBlank()) {
            throw new IllegalStateException("bakong.base-url is not configured!");
        }

        return RestClient.builder()
                .baseUrl(baseUrlbakong)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bakongToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}