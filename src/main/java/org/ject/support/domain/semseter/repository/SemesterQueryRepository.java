package org.ject.support.domain.semseter.repository;

import java.util.Optional;

public interface SemesterQueryRepository {
    Optional<Long> findOngoingSemester();
}
