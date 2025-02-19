package org.ject.support.external.email;

import lombok.RequiredArgsConstructor;
import org.ject.support.common.security.AuthPrincipal;
import org.ject.support.external.email.EmailDto.SendEmailRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailSendService emailSendService;

    @PostMapping("/send/auth")
    public void sendAuthEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        emailSendService.sendAuthCodeEmail(sendEmailRequest.getEmail());
    }
}
