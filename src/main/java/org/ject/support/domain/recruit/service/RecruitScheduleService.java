package org.ject.support.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.recruit.domain.Recruit;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RecruitScheduleService {

    private final TaskScheduler recruitScheduler;
    private final RecruitFlagService recruitFlagService;

    public void scheduleRecruitOpen(Recruit recruit) {
        Instant triggerTime = recruit.getStartDate()
                .atZone(ZoneId.systemDefault())
                .toInstant();

        recruitScheduler.schedule(() -> {
            recruitFlagService.setRecruitFlag(recruit);
        }, triggerTime);
    }
}
