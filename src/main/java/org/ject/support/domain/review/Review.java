package org.ject.support.domain.review;

import jakarta.persistence.*;
import org.ject.support.common.entity.BaseTimeEntity;

@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2083, nullable = false)
    private String linkUrl;
}
