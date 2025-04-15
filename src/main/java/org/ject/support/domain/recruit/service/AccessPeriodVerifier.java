package org.ject.support.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ject.support.common.exception.GlobalErrorCode;
import org.ject.support.common.exception.GlobalException;
import org.ject.support.common.util.PeriodAccessible;
import org.ject.support.domain.member.JobFamily;
import org.ject.support.domain.recruit.dto.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessPeriodVerifier {
    private final RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(org.ject.support.common.util.PeriodAccessible) && @annotation(target)")
    public Object checkRecruitmentPeriod(ProceedingJoinPoint joinPoint, PeriodAccessible target) throws Throwable {
        // 모든 직군에 대한 요청인 경우, 하나의 어떤 RECRUIT_FLAG만 있어도 허용
        if (target.permitAllJob()) {
            boolean isAnyRecruiting = Arrays.stream(JobFamily.values())
                    .anyMatch(jobFamily -> Boolean.parseBoolean(redisTemplate.opsForValue()
                            .get(getKeyName(jobFamily.name()))));
            validateRecruiting(isAnyRecruiting);
            return joinPoint.proceed();
        }

        // 특정 직군을 파라미터로 받은 경우, 해당 직군에 대한 모집 여부로 판단
        JobFamily jobFamily = extractJobFamily(joinPoint);
        boolean isRecruiting = Boolean.parseBoolean(redisTemplate.opsForValue()
                .get(getKeyName(jobFamily.name())));
        validateRecruiting(isRecruiting);
        return joinPoint.proceed();
    }

    private String getKeyName(String jobFamilyName) {
        return String.format("%s%s", Constants.RECRUIT_FLAG_PREFIX, jobFamilyName);
    }

    private void validateRecruiting(boolean isRecruiting) {
        if (Boolean.FALSE.equals(isRecruiting)) {
            throw new GlobalException(GlobalErrorCode.OVER_PERIOD);
        }
    }

    private JobFamily extractJobFamily(ProceedingJoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof JobFamily)
                .map(JobFamily.class::cast)
                .findFirst()
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MISS_REQUIRED_JOB_FAMILY_PARAMETER));
    }
}
