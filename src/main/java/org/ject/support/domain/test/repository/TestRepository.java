package org.ject.support.domain.test.repository;

import java.util.List;
import org.ject.support.domain.test.dto.TestDto;
import org.ject.support.domain.test.entity.Test;

public interface TestRepository {
    Test findById(Long id);

    void save(TestDto testDto);

    void saveBulk(List<TestDto> testDtos);
}
