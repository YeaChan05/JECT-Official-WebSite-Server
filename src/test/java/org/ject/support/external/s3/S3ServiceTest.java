package org.ject.support.external.s3;

import org.ject.support.domain.file.dto.CreatePresignedUrlResponse;
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

    @Test
    @DisplayName("create pre-signed url")
    void create_presigned_url() throws MalformedURLException {
        // given
        Long memberId = 123L;
        String fileName = "test.pdf";
        String expectedKeyName = String.format("%s/%s", memberId, "uuid_test.pdf");
        String expectedUrl = String.format("%s%s", "https://s3.test.com/", expectedKeyName);
        Instant expirationTime = Instant.now().plusSeconds(600);

        PresignedPutObjectRequest mockRequest = mock(PresignedPutObjectRequest.class);
        when(mockRequest.url()).thenReturn(URI.create(expectedUrl).toURL());
        when(mockRequest.expiration()).thenReturn(expirationTime);

        when(s3Presigner.presignPutObject((PutObjectPresignRequest) any())).thenReturn(mockRequest);

        // when
        CreatePresignedUrlResponse response = s3Service.createPresignedUrl(memberId, fileName);

        // then
        assertThat(response.keyName()).contains(fileName);
        assertThat(response.presignedUrl()).contains(expectedUrl);
        assertThat(response.presignedUrl()).isEqualTo(expectedUrl);
        assertThat(response.expiration()).isEqualTo(LocalDateTime.ofInstant(expirationTime, ZoneId.systemDefault()));

        verify(s3Presigner, times(1)).presignPutObject((PutObjectPresignRequest) any());
    }
}