package org.ject.support.domain.jectalk;

import jakarta.persistence.*;
import org.ject.support.common.entity.BaseTimeEntity;

@Entity
public class Jectalk extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 2083, nullable = false)
    private String youtubeUrl;

    @Column(length = 2083)
    private String imageUrl;
}
