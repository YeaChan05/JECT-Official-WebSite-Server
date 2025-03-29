package org.ject.support.domain.recruit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ject.support.domain.base.BaseTimeEntity;

@Getter
@Entity
@Table(name = "semester")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Semester extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "semester_id")
    private Long id;

    @Column(name = "semester_name", length = 20, nullable = false)
    private String name;

    @Column(name = "is_recruiting", nullable = false)
    @Builder.Default
    private Boolean isRecruiting = true;

}
