package org.ject.support.domain.project.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ject.support.domain.project.entity.ProjectIntro.Category.DEV;
import static org.ject.support.domain.project.entity.ProjectIntro.Category.SERVICE;

class ProjectIntroTest {

    @Test
    @DisplayName("프로젝트 소개서의 카테고리가 일치하는지 확인")
    void is_category() {
        // given
        ProjectIntro serviceIntro = createProjectIntro(1L, SERVICE, "image1.png");
        ProjectIntro devIntro = createProjectIntro(2L, DEV, "image2.png");

        // when, then
        assertThat(serviceIntro.isCategory(SERVICE)).isTrue();
        assertThat(serviceIntro.isCategory(DEV)).isFalse();
        assertThat(devIntro.isCategory(DEV)).isTrue();
        assertThat(devIntro.isCategory(SERVICE)).isFalse();
    }

    private ProjectIntro createProjectIntro(long id, ProjectIntro.Category dev, String image) {
        return ProjectIntro.builder()
                .id(id)
                .category(dev)
                .imageUrl(image)
                .sequence(1)
                .build();
    }
}