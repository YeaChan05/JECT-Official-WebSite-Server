package org.ject.support.domain.recruit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;

public record RecruitRegisterRequest(JobFamily jobFamily) {
    public Recruit toEntity(Long ongoingSemesterId, final LocalDate startDate, final LocalDate endDate) {
        return Recruit.builder()
                .semesterId(ongoingSemesterId)
                .jobFamily(jobFamily)
                .startDate(LocalDateTime.of(startDate, LocalTime.of(0, 0)))
                .endDate(LocalDateTime.of(endDate, LocalTime.of(23, 59)))
                .build();
    }
}
