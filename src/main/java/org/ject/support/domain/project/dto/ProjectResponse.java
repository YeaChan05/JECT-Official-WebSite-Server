package org.ject.support.domain.project.dto;

import lombok.Builder;

@Builder
public record ProjectResponse(Long id, String thumbnailUrl, String name, String summary, String period) {
}
