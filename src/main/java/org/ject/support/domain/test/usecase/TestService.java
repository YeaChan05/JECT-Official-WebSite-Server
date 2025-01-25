package org.ject.support.domain.test.usecase;

import lombok.RequiredArgsConstructor;
import org.ject.support.domain.test.dto.TestDto;
import org.ject.support.domain.test.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService implements TestUseCase {
    private final TestRepository testRepository;

    @Override
    @Transactional
    public void save(TestDto testDto) {
        testRepository.save(testDto);
    }

    @Override
    public TestDto get(final Long id) {
        return testRepository.findById(id)
                .toDto();
    }
}
