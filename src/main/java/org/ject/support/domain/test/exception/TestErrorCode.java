package org.ject.support.domain.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ject.support.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements ErrorCode {
    NOT_FOUND("TEST_001", "Test not found");
    private final String code;
    private final String message;
}
