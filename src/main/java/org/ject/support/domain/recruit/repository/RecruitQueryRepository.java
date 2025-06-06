package org.ject.support.domain.recruit.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;

public interface RecruitQueryRepository {
    Optional<Recruit> findActiveRecruitByJobFamily(JobFamily jobFamily, LocalDateTime now);
}
