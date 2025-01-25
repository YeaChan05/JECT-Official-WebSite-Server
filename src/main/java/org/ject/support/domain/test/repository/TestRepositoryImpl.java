package org.ject.support.domain.test.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.test.entity.Test;
import org.ject.support.domain.test.exception.TestErrorCode;
import org.ject.support.domain.test.exception.TestException;
import org.ject.support.domain.test.dto.TestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepository {
    private final JpaTestRepository jpaTestRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Test findById(final Long id) {
        return jpaTestRepository.findById(id)
                .orElseThrow(() -> new TestException(TestErrorCode.NOT_FOUND));
    }

    @Override
    public void save(final TestDto testDto) {
        Test test = Test.builder()
                .name(testDto.name())
                .build();
        jpaTestRepository.save(test);
    }

    @Override
    public void saveBulk(final List<TestDto> testDtos) {
        String sql = "INSERT INTO TEST(name) VALUES (?)";
        jdbcTemplate.batchUpdate(sql, testDtos, testDtos.size(), (ps, testDto) -> {
            ps.setString(1, testDto.name());
        });
    }
}
