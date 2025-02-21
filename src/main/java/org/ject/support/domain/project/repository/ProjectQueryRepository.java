package org.ject.support.domain.project.repository;

import org.ject.support.domain.project.dto.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectQueryRepository {

    Page<ProjectResponse> findProjectsBySemester(final String semester, Pageable pageable);
}
