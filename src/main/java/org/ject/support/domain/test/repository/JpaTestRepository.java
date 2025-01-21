package org.ject.support.domain.test.repository;

import org.ject.support.domain.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestRepository extends JpaRepository<Test, Long> {
}
