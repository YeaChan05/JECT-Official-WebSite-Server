package org.ject.support.domain.recruit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.domain.Recruit;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.ject.support.domain.recruit.domain.QRecruit.recruit;

@Repository
@RequiredArgsConstructor
public class RecruitQueryRepositoryImpl implements RecruitQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Recruit> findActiveRecruitByJobFamily(final JobFamily jobFamily, final LocalDateTime now) {
        Recruit fetched = jpaQueryFactory.selectFrom(recruit)
                .where(recruit.jobFamily.eq(jobFamily), recruit.startDate.before(now).and(recruit.endDate.after(now)))
                .fetchFirst();
        return Optional.ofNullable(fetched);
    }
}
