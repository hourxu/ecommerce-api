package ecommerce.project.security.jwt;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String secretKey,
        long accessExpireSeconds,
        long refreshExpireSeconds
) {
}
