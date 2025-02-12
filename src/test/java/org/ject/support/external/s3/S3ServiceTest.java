package org.ject.support.external.s3;

import org.ject.support.domain.file.dto.CreatePresignedUrlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Presigner s3Presigner;

    @InjectMocks
    private S3Service s3Service;

    private TestParameter testParameter;

    @BeforeEach
    void setUp() throws MalformedURLException {
        testParameter = new TestParameter(123L, "test.pdf", Instant.now().plusSeconds(600));
        PresignedPutObjectRequest mockRequest = mock(PresignedPutObjectRequest.class);
        when(mockRequest.url()).thenReturn(URI.create(testParameter.expectedUrl).toURL());
        when(mockRequest.expiration()).thenReturn(testParameter.expirationTime);
        when(s3Presigner.presignPutObject((PutObjectPresignRequest) any())).thenReturn(mockRequest);
    }

    @Test
    @DisplayName("create pre-signed url")
    void create_presigned_url() {
        // when
        CreatePresignedUrlResponse response =
                s3Service.createPresignedUrl(testParameter.memberId, testParameter.fileName);

        // then
        assertThat(response.keyName()).contains(testParameter.fileName);
        assertThat(response.presignedUrl()).contains(testParameter.expectedUrl);
        assertThat(response.presignedUrl()).isEqualTo(testParameter.expectedUrl);
        assertThat(response.expiration())
                .isEqualTo(LocalDateTime.ofInstant(testParameter.expirationTime, ZoneId.systemDefault()));
    }

    @Test
    @DisplayName("create key name")
    void create_key_name() {
        // when
        CreatePresignedUrlResponse response =
                s3Service.createPresignedUrl(testParameter.memberId, testParameter.fileName);

        // then
        assertThat(response.keyName()).contains(testParameter.memberId.toString());
        assertThat(removePrefix(response.keyName())).startsWith(testParameter.fileName);
        assertThat(response.keyName()).contains("_");
    }

    private String removePrefix(String keyName) {
        String prefix = String.format("%s/", testParameter.memberId);
        return keyName.replace(prefix, "");
    }

    static class TestParameter {
        Long memberId;
        String fileName;
        Instant expirationTime;
        String expectedKeyName;
        String expectedUrl;

        public TestParameter(Long memberId, String fileName, Instant expirationTime) {
            this.memberId = memberId;
            this.fileName = fileName;
            this.expectedKeyName = String.format("%s/%s", memberId, "test.pdf_uuid");
            this.expectedUrl = String.format("%s%s", "https://s3.test.com/", expectedKeyName);
            this.expirationTime = expirationTime;
        }
    }
}