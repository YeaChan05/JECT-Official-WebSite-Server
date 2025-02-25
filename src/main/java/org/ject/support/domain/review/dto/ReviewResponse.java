package org.ject.support.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ReviewResponse(String linkUrl, String title, String description) {

    @QueryProjection
    public ReviewResponse {
    }
}
