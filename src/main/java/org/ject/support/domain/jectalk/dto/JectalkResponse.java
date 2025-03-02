package org.ject.support.domain.jectalk.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record JectalkResponse(String name,
                            String youtubeUrl,
                            String imageUrl) {

    @QueryProjection
    public JectalkResponse {
    }
}
