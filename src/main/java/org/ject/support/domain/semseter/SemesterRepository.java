package org.ject.support.domain.semseter;

import org.ject.support.domain.recruit.domain.Semester;
import org.ject.support.domain.semseter.repository.SemesterQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long>, SemesterQueryRepository {
}
