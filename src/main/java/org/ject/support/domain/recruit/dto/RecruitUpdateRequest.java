package org.ject.support.domain.recruit.dto;

import org.ject.support.domain.member.JobFamily;

import java.time.LocalDateTime;

public record RecruitUpdateRequest(JobFamily jobFamily,
                                   LocalDateTime startDate,
                                   LocalDateTime endDate) {
}
