package org.ject.support.domain.ministudy.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record MiniStudyResponse(String name,
                              String linkUrl,
                              String imageUrl) {

    @QueryProjection
    public MiniStudyResponse {
    }
}
