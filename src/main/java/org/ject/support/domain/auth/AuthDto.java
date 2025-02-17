package org.ject.support.domain.auth;

import lombok.Data;
import lombok.Getter;

public class AuthDto {

    @Data
    @Getter
    public static class VerifyAuthCodeRequest {
        private String name;
        private String email;
        private String phoneNumber;
        private String authCode;
    }

    @Data
    @Getter
    public static class AuthCodeResponse {
        private final String accessToken;
        private final String refreshToken;
    }
}
