package org.ject.support.domain.test.usecase;

import org.ject.support.domain.test.dto.TestDto;

public interface TestUseCase {
    void save(TestDto testDto);
    TestDto get(Long id);
}
