package org.ject.support.domain.recruitment;

import jakarta.persistence.*;
import org.ject.support.common.entity.BaseTimeEntity;
import org.ject.support.domain.member.JobFamily;

@Entity
public class Recruitment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(45)", nullable = false)
    private JobFamily jobFamily;
}
