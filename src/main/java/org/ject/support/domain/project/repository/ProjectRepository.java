package org.ject.support.domain.project.repository;

import org.ject.support.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findProjectsBySemester(String semester);
}
