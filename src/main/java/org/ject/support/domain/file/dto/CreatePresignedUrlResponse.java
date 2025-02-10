package org.ject.support.domain.file.dto;

import java.time.LocalDateTime;

public record CreatePresignedUrlResponse(String keyName, String presignedUrl, LocalDateTime expiration) {
}
