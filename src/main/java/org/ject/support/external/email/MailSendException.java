package org.ject.support.external.email;

import org.ject.support.common.exception.BusinessException;
import org.ject.support.common.exception.ErrorCode;

public class MailSendException extends BusinessException {
    public MailSendException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
