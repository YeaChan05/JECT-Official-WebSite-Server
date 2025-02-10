package org.ject.support.external.s3;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.file.dto.CreatePresignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    /**
     * 사용자가 첨부한 파일 이름과 해당 사용자의 식별자를 토대로 Pre-signed URL 생성
     */
    public CreatePresignedUrlResponse createPresignedUrl(Long memberId, String fileName) {
        String uniqueFileName = String.format("%s_%s", UUID.randomUUID(), fileName);
        String keyName = String.format("%s/%s", memberId, uniqueFileName);

        PutObjectRequest objectRequest = createPutObjectRequest(keyName);
        PutObjectPresignRequest presignRequest = createPutObjectPresignRequest(10, objectRequest);
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return new CreatePresignedUrlResponse(
                keyName,
                presignedRequest.url().toExternalForm(),
                LocalDateTime.ofInstant(presignedRequest.expiration(), ZoneId.systemDefault()));
    }

    private PutObjectRequest createPutObjectRequest(String keyName) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(keyName)
                .build();
    }

    private PutObjectPresignRequest createPutObjectPresignRequest(long expireMinutes, PutObjectRequest objectRequest) {
        return PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(expireMinutes))
                .putObjectRequest(objectRequest)
                .build();
    }
}
