package org.ject.support.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ject.support.domain.base.BaseTimeEntity;
import org.ject.support.domain.member.Team;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 20, nullable = false)
    private String semester;

    @Column(length = 100, nullable = false)
    private String summary;

    private String techStack;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 2083)
    private String thumbnailUrl;

    @Column(length = 2083)
    private String serviceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
