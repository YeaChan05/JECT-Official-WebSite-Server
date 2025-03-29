package org.ject.support.domain.recruit.dto;

import java.util.List;

public record SemesterRegisterRequest(String name, List<RecruitRegisterRequest> recruitRegisterRequests) {
}
