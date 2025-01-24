package org.ject.support.testconfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.util.StopWatch;

@Slf4j
public class ThroughputTestExtension implements BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        testMethod.setAccessible(true);
        ThroughputTest annotation = testMethod.getAnnotation(ThroughputTest.class);

        if (annotation != null) {
            int request = annotation.request();
            long timeout = annotation.timeout();
            AtomicInteger count = new AtomicInteger();
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            try (ExecutorService executorService = Executors.newFixedThreadPool(request)) {
                executorService.execute(() -> {
                    for (int j = 0; j < request; j++) {
                        try {
                            testMethod.invoke(context.getRequiredTestInstance());
                            count.getAndIncrement();
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                executorService.shutdown();
                executorService.awaitTermination(timeout, TimeUnit.MICROSECONDS);
            }
            stopWatch.stop();
            log.info("\n=====================\n"
                     + "전체 실행 시간: {}s\n"
                     + "요청 수량: {}"
                     + "\n=====================\n",
                    stopWatch.getTotalTimeSeconds(),
                    count.get());
        }
    }
}
