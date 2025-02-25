package org.ject.support.domain.member.repository;

import org.ject.support.domain.member.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
