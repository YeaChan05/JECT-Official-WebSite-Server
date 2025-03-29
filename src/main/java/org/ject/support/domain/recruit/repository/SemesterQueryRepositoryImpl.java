package org.ject.support.domain.recruit.repository;

import static org.ject.support.domain.recruit.domain.QSemester.semester;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SemesterQueryRepositoryImpl implements SemesterQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Long> findOngoingSemester() {
        queryFactory.select(semester.id)
                .from(semester)
                .where(semester.isOngoing.eq(true))
                .fetchOne();
        return Optional.empty();
    }
}
