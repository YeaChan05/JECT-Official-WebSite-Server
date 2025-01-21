package org.ject.support.domain.test.usecase;

import java.util.UUID;
import org.ject.support.domain.test.dto.TestDto;
import org.ject.support.testconfig.RabbitTestContainer;
import org.ject.support.testconfig.ThroughputTest;
import org.ject.support.testconfig.ThroughputTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(ThroughputTestExtension.class)
class TestServiceTest  implements RabbitTestContainer {
    @Autowired
    private TestService testService;

    @Test
    @ThroughputTest(request = 10000)
    @DisplayName("save 메서드 테스트")
    void testConcurrency() {
        testService.save(new TestDto(UUID.randomUUID().toString()));
    }

    @Test
    @ThroughputTest(request = 10000)
    @DisplayName("save bulk")
    void bulk() {
        testService.saveBulk(new TestDto(UUID.randomUUID().toString()));
    }
}
