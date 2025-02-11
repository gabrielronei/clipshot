package br.com.fiap.clipshot.core.video.upload.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class S3StorageGateway implements StorageGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3StorageGateway.class);
    private static final String BUCKET = "clipshot-videos";
    private final S3Client s3Client;

    public S3StorageGateway(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String upload(MultipartFile file) {
        final var key = file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    .contentLength(file.getSize())
                    .expires(Instant.now().plus(3, ChronoUnit.DAYS))
                    .storageClass(StorageClass.STANDARD)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(file.getInputStream().readAllBytes()));

            LOGGER.info("[S3StorageGateway] File uploaded to S3: {}", key);
            GetUrlRequest request = GetUrlRequest.builder().bucket(BUCKET).key(key).build();
            return s3Client.utilities().getUrl(request).toExternalForm();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}