package org.ject.support.domain.semseter.exception;

import org.ject.support.common.exception.BusinessException;
import org.ject.support.common.exception.ErrorCode;

public class SemesterException extends BusinessException {
    public SemesterException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
