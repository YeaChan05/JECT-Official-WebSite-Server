package org.ject.support.domain.test.exception;

import org.ject.support.common.exception.BusinessException;
import org.ject.support.common.exception.ErrorCode;

public class TestException extends BusinessException {
    public TestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
