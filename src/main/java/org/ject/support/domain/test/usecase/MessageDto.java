package org.ject.support.domain.test.usecase;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class MessageDto {
    private final String message;
    private final LocalDateTime sendTime;

    public MessageDto(final String message) {
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }
}
