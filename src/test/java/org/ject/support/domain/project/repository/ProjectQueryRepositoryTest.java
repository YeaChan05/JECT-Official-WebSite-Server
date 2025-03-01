package org.ject.support.domain.project.repository;

import java.time.LocalDate;
import java.util.List;
import org.ject.support.domain.member.entity.Team;
import org.ject.support.domain.member.repository.TeamRepository;
import org.ject.support.domain.project.dto.ProjectResponse;
import org.ject.support.domain.project.entity.Project;
import org.ject.support.testconfig.QueryDslTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDslTestConfig.class)
@DataJpaTest
class ProjectQueryRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Team team1, team2, team3;

    @BeforeEach
    void setUp() {
        team1 = Team.builder().name("team1").build();
        team2 = Team.builder().name("team2").build();
        team3 = Team.builder().name("team3").build();
        teamRepository.saveAll(List.of(team1, team2, team3));
    }

    @Test
    @DisplayName("기수별 프로젝트 목록 조회")
    void findProjectsBySemester() {
        // given
        Project project1 = createProject("1기", team1);
        Project project2 = createProject("1기", team2);
        Project project3 = createProject("2기", team3);
        projectRepository.saveAll(List.of(project1, project2, project3));

        // when
        Page<ProjectResponse> result = projectRepository.findProjectsBySemester("1기", PageRequest.of(0, 30));

        // then
        assertThat(result).isNotNull();

        List<ProjectResponse> responses = result.getContent();
        assertThat(responses).hasSize(2);

        ProjectResponse firstResponse = responses.get(0);
        assertThat(firstResponse.name()).isEqualTo("projectName");
        assertThat(firstResponse.summary()).isEqualTo("summary");
        assertThat(firstResponse.thumbnailUrl()).isEqualTo("https://test.net/thumbnail.png");
        assertThat(firstResponse.startDate()).isEqualTo(LocalDate.of(2025, 3, 2));
        assertThat(firstResponse.endDate()).isEqualTo(LocalDate.of(2025, 6, 30));
    }

    private Project createProject(String semester, Team team) {
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
