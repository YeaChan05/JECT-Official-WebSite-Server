package org.ject.support.external.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ject.support.common.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements ErrorCode {
    MAIL_SEND_FAILURE("MAIL_SEND_FAILURE", "메일 전송에 실패하였습니다."),;
    private final String code ;
    private final String message;
}
