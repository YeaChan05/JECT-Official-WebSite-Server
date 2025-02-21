package org.ject.support.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.project.dto.ProjectResponse;
import org.ject.support.domain.project.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * 주어진 기수의 프로젝트를 모두 조회합니다.
     */
    @Transactional(readOnly = true)
    public Page<ProjectResponse> findProjectsBySemester(String semester, Pageable pageable) {
        return projectRepository.findProjectsBySemester(semester, pageable);
    }
}
