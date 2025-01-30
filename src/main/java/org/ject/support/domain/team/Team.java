package org.ject.support.domain.team;

import jakarta.persistence.*;
import org.ject.support.common.entity.BaseTimeEntity;

@Entity
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;
}
