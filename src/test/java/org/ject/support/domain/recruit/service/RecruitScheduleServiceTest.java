package org.ject.support.domain.recruit.service;

import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecruitScheduleServiceTest {

    @InjectMocks
    RecruitScheduleService recruitScheduleService;

    @Mock
    TaskScheduler recruitScheduler;

    @Mock
    RecruitFlagService recruitFlagService;

    @Test
    @DisplayName("schedule recruit open at start date")
    void schedule_recruit_open_at_start_date() {
        // given
        Recruit recruit = Recruit.builder()
                .id(1L)
                .semesterId(1L)
                .jobFamily(JobFamily.BE)
                .startDate(LocalDateTime.now().plusDays(3))
                .endDate(LocalDateTime.now().plusDays(5))
                .build();

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> instantCaptor = ArgumentCaptor.forClass(Instant.class);

        // when
        recruitScheduleService.scheduleRecruitOpen(recruit);

        // then
        verify(recruitScheduler).schedule(runnableCaptor.capture(), instantCaptor.capture());

        Runnable scheduledTask = runnableCaptor.getValue();
        Instant triggerTime = instantCaptor.getValue();

        Instant expectedTriggerTime = recruit.getStartDate().atZone(ZoneId.systemDefault()).toInstant();

        assertThat(triggerTime).isEqualTo(expectedTriggerTime);

        scheduledTask.run();
        verify(recruitFlagService).setRecruitFlag(recruit);
    }
}
