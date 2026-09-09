package ecommerce.project.config;


import ecommerce.project.security.jwt.JwtProperties;
import io.github.tongbora.bakong.config.BakongProperties;
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

    @Value("${bakong.token}")
    private String token;
    @Value("${bakong.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION,token)
                .defaultHeader(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
