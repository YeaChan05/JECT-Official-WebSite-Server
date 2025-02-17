package org.ject.support.external.email;

import lombok.Data;
import lombok.Getter;

public class EmailDto {

    @Data
    @Getter
    public static class SendEmailRequest {
        private String email;
    }
}
