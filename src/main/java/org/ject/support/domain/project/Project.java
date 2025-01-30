package org.ject.support.domain.project;

import jakarta.persistence.*;
import org.ject.support.common.entity.BaseTimeEntity;
import org.ject.support.domain.team.Team;

import java.time.LocalDate;

@Entity
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    private String techStack;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @Column(length = 2083)
    private String thumbnailUrl;

    @Column(length = 2083)
    private String serviceUrl;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
