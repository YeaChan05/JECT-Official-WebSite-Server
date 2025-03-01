package org.ject.support.domain.ministudy.repository;

import org.ject.support.domain.ministudy.entity.MiniStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniStudyRepository extends JpaRepository<MiniStudy, Long>, MiniStudyQueryRepository {
}
