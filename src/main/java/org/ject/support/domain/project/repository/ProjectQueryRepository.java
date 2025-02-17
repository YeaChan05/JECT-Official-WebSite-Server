package org.ject.support.domain.project.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.project.dto.ProjectResponse;
import org.ject.support.domain.project.dto.QProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ject.support.domain.project.entity.QProject.project;

@Repository
@RequiredArgsConstructor
public class ProjectQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ProjectResponse> findProjectsBySemester(final String semester, Pageable pageable) {
        List<ProjectResponse> content = queryFactory.select(new QProjectResponse(
                        project.id,
                        project.thumbnailUrl,
                        project.name,
                        project.summary,
                        project.startDate,
                        project.endDate
                ))
                .from(project)
                .where(project.semester.eq(semester))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(project.count())
                .from(project);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
