package org.ject.support.domain.jectalk.repository;

import org.ject.support.domain.jectalk.entity.Jectalk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JectalkRepository extends JpaRepository<Jectalk, Long>, JectalkQueryRepository {
}
