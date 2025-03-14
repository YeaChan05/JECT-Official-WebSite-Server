package org.ject.support.domain.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ject.support.domain.auth.AuthDto.PinLoginRequest;
import org.ject.support.domain.auth.AuthDto.PinLoginResponse;
import org.ject.support.domain.auth.AuthDto.VerifyAuthCodeOnlyResponse;
import org.ject.support.domain.auth.AuthDto.VerifyAuthCodeRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 인증번호 검증 API
     * 인증번호 검증만 수행하고 임시 토큰을 발급합니다.
     */
    @PostMapping("/code")
    @PreAuthorize("permitAll()")
    public VerifyAuthCodeOnlyResponse verifyAuthCode(@RequestBody VerifyAuthCodeRequest request) {
        return authService.verifyEmailByAuthCodeOnly(request.email(), request.authCode());
    }
    
    /**
     * PIN 로그인 API
     * 이메일과 PIN 번호로 로그인하고 액세스 토큰과 리프레시 토큰을 발급합니다.
     */
    @PostMapping("/login/pin")
    @PreAuthorize("permitAll()")
    public PinLoginResponse loginWithPin(@RequestBody @Valid PinLoginRequest request) {
        return authService.loginWithPin(request.email(), request.pin());
    }

    @GetMapping("/login/exist")
    @PreAuthorize("permitAll()")
    public boolean isExistMember(@RequestParam String email) {
        return authService.isExistMember(email);
    }
}
