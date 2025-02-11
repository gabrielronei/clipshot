package br.com.fiap.clipshot.core.infra.cloud;

import br.com.fiap.clipshot.core.video.upload.storage.S3StorageGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@Profile("!test")
public class AwsConfiguration {

    @Value("${aws.access.key.id}")
    private String accessKey;

    @Value("${aws.secret.access.key}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket.url}")
    private String url;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(url))
                .region(Region.of(region))
                .credentialsProvider(this::awsBasicCredentials)
                .build();
    }

    @Bean
    public S3StorageGateway s3StorageGateway(S3Client s3Client) {
        return new S3StorageGateway(s3Client);
    }

    @Bean
    public AwsBasicCredentials awsBasicCredentials() {
        return AwsBasicCredentials.create(accessKey, secretAccessKey);
    }

}