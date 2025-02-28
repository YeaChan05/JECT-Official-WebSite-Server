package org.ject.support.domain.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import org.ject.support.domain.project.entity.ProjectIntro;

@Builder
public record ProjectIntroResponse(String imageUrl, int sequence) {

    @QueryProjection
    public ProjectIntroResponse {
    }

    public static ProjectIntroResponse toResponse(ProjectIntro projectIntro) {
        return ProjectIntroResponse.builder()
                .imageUrl(projectIntro.getImageUrl())
                .sequence(projectIntro.getSequence())
                .build();
    }
}
