package org.ject.support.domain.recruit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ject.support.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum ApplyErrorCode implements ErrorCode {
    DUPLICATE_JOB_FAMILY("DUPLICATE_JOB_FAMILY", "변경하려는 직군이 기존 직군과 동일합니다."),
    EXCEEDED_PORTFOLIO_MAX_SIZE("EXCEEDED_PORTFOLIO_SIZE", "첨부 가능한 포트폴리오 최대 용량을 초과했습니다.");

    private final String code;
    private final String message;
}
