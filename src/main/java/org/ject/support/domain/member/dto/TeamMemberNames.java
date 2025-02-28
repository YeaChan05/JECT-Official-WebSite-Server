package org.ject.support.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;

public record TeamMemberNames(List<String> projectManagers,
                              List<String> productDesigners,
                              List<String> frontendDevelopers,
                              List<String> backendDevelopers) {

    @QueryProjection
    public TeamMemberNames {
    }
}
