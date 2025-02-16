package org.ject.support.domain.project.service;

import org.ject.support.domain.member.Team;
import org.ject.support.domain.member.TeamRepository;
import org.ject.support.domain.project.dto.ProjectResponse;
import org.ject.support.domain.project.entity.Project;
import org.ject.support.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Test
    void findProjectsBySemester() {
        // given
        Team team1 = teamRepository.save(Team.builder().name("team1").build());
        Team team2 = teamRepository.save(Team.builder().name("team2").build());
        Team team3 = teamRepository.save(Team.builder().name("team3").build());
        Project project1 = getProject("1기", team1);
        Project project2 = getProject("1기", team2);
        Project project3 = getProject("2기", team3);
        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        // when
        List<ProjectResponse> result = projectService.findProjectsBySemester("1기");

        // then
        assertThat(result.size()).isEqualTo(2);

        ProjectResponse project1Response = result.get(0);
        assertThat(project1Response.name()).isEqualTo("projectName");
        assertThat(project1Response.summary()).isEqualTo("summary");
        assertThat(project1Response.thumbnailUrl()).isEqualTo("https://test.net/thumbnail.png");
        assertThat(project1Response.period()).isEqualTo("2025-03-02 ~ 2025-06-30");
    }

    private Project getProject(String semester, Team team) {
        return Project.builder()
                .name("projectName")
                .thumbnailUrl("https://test.net/thumbnail.png")
                .semester(semester)
                .summary("summary")
                .startDate(LocalDate.of(2025, 3, 2))
                .endDate(LocalDate.of(2025, 6, 30))
                .team(team)
                .build();
    }
}