package org.ject.support.domain.recruit.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.ject.support.common.exception.GlobalException;
import org.ject.support.common.util.PeriodAccessible;
import org.ject.support.domain.member.JobFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessPeriodVerifierTest {

    @InjectMocks
    AccessPeriodVerifier accessPeriodVerifier;

    @Mock
    ProceedingJoinPoint joinPoint;

    @Mock
    PeriodAccessible target;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("하나의 직군이라도 모집중인 상태에서 permitAllJob이 true면 통과")
    void permit_all_job_success_by_recruiting_anyone() throws Throwable {
        // given
        when(target.permitAllJob()).thenReturn(true); // 모든 직군에 대한 요청
        when(valueOperations.get(anyString())).thenReturn("false");
        when(valueOperations.get("RECRUIT_FLAG:FE")).thenReturn("true"); // 하나라도 모집중
        when(joinPoint.proceed()).thenReturn("OK");

        // when
        Object result = accessPeriodVerifier.checkRecruitmentPeriod(joinPoint, target);

        // then
        assertThat(result).isEqualTo("OK");
    }

    @Test
    @DisplayName("모집중인 직군이 없는 상태에서 permitAllJob이 true이면 실패")
    void permit_all_job_fail_by_not_recruiting_all() {
        // given
        when(target.permitAllJob()).thenReturn(true); // 모든 직군에 대한 요청
        when(valueOperations.get(anyString())).thenReturn("false");

        // when, then
        assertThatThrownBy(() -> accessPeriodVerifier.checkRecruitmentPeriod(joinPoint, target))
                .isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("permitAllJob이 false인 상태에서 모집중인 직군을 파라미터로 전달하면 통과")
    void check_success_by_has_job_family() throws Throwable {
        // given
        when(target.permitAllJob()).thenReturn(false);
        when(joinPoint.getArgs()).thenReturn(new Object[]{JobFamily.BE});
        when(valueOperations.get(anyString())).thenReturn("false");
        when(valueOperations.get("RECRUIT_FLAG:BE")).thenReturn("true");
        when(joinPoint.proceed()).thenReturn("OK");

        // when
        Object result = accessPeriodVerifier.checkRecruitmentPeriod(joinPoint, target);

        // then
        assertThat(result).isEqualTo("OK");
    }

    @Test
    @DisplayName("permitAllJob이 false인 상태에서 모집중이지 않은 직군을 파라미터로 전달하면 실패")
    void test_method_name() {
        // given
        when(target.permitAllJob()).thenReturn(false);
        when(joinPoint.getArgs()).thenReturn(new Object[]{JobFamily.BE});
        when(valueOperations.get(anyString())).thenReturn("false");

        // when, then
        assertThatThrownBy(() -> accessPeriodVerifier.checkRecruitmentPeriod(joinPoint, target))
                .isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("permitAll이 false인데 JobFamily 파라미터 없으면 실패")
    void check_fail_by_has_not_job_family() {
        // given
        when(target.permitAllJob()).thenReturn(false);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"string", 123}); // JobFamily 없음

        // when, then
        assertThatThrownBy(() -> accessPeriodVerifier.checkRecruitmentPeriod(joinPoint, target))
                .isInstanceOf(GlobalException.class);
    }
}