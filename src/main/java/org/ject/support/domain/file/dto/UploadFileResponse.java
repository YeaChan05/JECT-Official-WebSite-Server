package org.ject.support.domain.file.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UploadFileResponse(String cdnUrl, String presignedUrl, LocalDateTime expiration) {
}
