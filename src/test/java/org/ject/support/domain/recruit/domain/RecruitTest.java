package org.ject.support.domain.recruit.domain;

import org.ject.support.domain.member.JobFamily;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RecruitTest {

    @Test
    @DisplayName("is recruiting period")
    void is_recruiting_period() {
        // given
        Recruit recruit = Recruit.builder()
                .semester("2025-1")
                .jobFamily(JobFamily.BE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .build();

        // when
        Boolean isRecruitingPeriod = recruit.isRecruitingPeriod();

        // then
        assertThat(isRecruitingPeriod).isTrue();
    }
}