package org.ject.support.domain.recruit.dto;

import java.time.LocalDateTime;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;
import org.springframework.format.annotation.DateTimeFormat;

public record RecruitRegisterRequest(JobFamily jobFamily,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH")
                                     LocalDateTime startDate,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH")
                                     LocalDateTime endDate) {
    public Recruit toEntity(Long ongoingSemesterId) {
        return Recruit.builder()
                .semesterId(ongoingSemesterId)
                .jobFamily(this.jobFamily)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
