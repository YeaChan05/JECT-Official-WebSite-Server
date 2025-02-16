package org.ject.support.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.project.dto.ProjectResponse;
import org.ject.support.domain.project.entity.Project;
import org.ject.support.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findProjectsBySemester(String semester) {
        return projectRepository.findProjectsBySemester(semester)
                .stream()
                .map(this::getProjectResponse)
                .toList();
    }

    private ProjectResponse getProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .thumbnailUrl(project.getThumbnailUrl())
                .name(project.getName())
                .summary(project.getSummary())
                .period(String.format("%s ~ %s", project.getStartDate(), project.getEndDate()))
                .build();
    }
}
