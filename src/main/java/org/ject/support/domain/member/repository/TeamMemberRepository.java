package org.ject.support.domain.member.repository;

import org.ject.support.domain.member.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
