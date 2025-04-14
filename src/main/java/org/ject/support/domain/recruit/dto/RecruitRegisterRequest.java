package org.ject.support.domain.recruit.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;

public record RecruitRegisterRequest(
        @NotNull(message = "직군은 필수입니다.")
        JobFamily jobFamily,

        @NotNull(message = "모집 시작일은 필수입니다.")
        @Future(message = "모집 시작일은 현재 시각보다 이후여야 합니다.")
        LocalDateTime startDate,

        @NotNull(message = "모집 종료일은 필수입니다.")
        @Future(message = "모집 종료일은 현재 시각보다 이후여야 합니다.")
        LocalDateTime endDate
) {
    public Recruit toEntity(Long ongoingSemesterId) {
        return Recruit.builder()
                .semesterId(ongoingSemesterId)
                .jobFamily(this.jobFamily)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
