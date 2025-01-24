package org.ject.support.testconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;

/**
 * 요청 쇄도에 대한 가용성 테스트를 수행할 메서드에 지정하는 어노테이션입니다 <br>
 * {@link ThroughputTestExtension}을 {@link org.junit.jupiter.api.extension.ExtendWith} 어노테이션으로 지정한 테스트 클래스에 한해 동작합니다
 * 전체 테스트 실행 기간에는 비활성화됩니다
 * @see ThroughputTestExtension
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Tag("throughput")
public @interface ThroughputTest {
    int request() default 1;
    long timeout() default 1000L;
}
