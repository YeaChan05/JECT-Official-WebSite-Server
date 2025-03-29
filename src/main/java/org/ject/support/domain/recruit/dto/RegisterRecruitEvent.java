package org.ject.support.domain.recruit.dto;

import java.util.List;

public record RegisterRecruitEvent(Long id, List<RecruitRegisterRequest> recruitRegisterRequests) {
}
