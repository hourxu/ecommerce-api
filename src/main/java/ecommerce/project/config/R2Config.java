package ecommerce.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class R2Config {

    @Value("${cloudflare.r2.account-id}")
    private String accountId;

    @Value("${cloudflare.r2.access-key}")
    private String accessKey;

    @Value("${cloudflare.r2.secret-key}")
    private String secretKey;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @Bean
    public S3Client s3Client() { //"S3client" the java tool your spring boot use communicate with cloud flare R2(storge)

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        accessKey,
                        secretKey
                );

        return S3Client.builder()
                .endpointOverride(
                        URI.create(
                                "https://" + accountId +
                                        ".r2.cloudflarestorage.com"
                        )
                )
                .region(Region.of("auto"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .chunkedEncodingEnabled(false)
                                .build()
                )
                .build();
    }
}