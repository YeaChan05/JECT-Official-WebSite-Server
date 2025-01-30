package org.ject.support.external.email;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final JavaMailSender mailSender;
    private final MessageGenerator messageGenerator;

    /**
     * 단건 email 전송
     */
    public void sendEmail(String to, EmailTemplate template, MailParameter parameter) {
        MimeMessagePreparator preparator = messageGenerator.generateMessagePreparator(to, template,
                parameter.getParam());
        sendMail(preparator);
    }

    /**
     * 단체 email 전송
     */
    public void sendEmailBulk(List<MailSendRequest> requests) {
        MimeMessagePreparator[] preparators = requests.stream()
                .map(request -> messageGenerator.generateMessagePreparator(request.to, request.template, request.parameter.getParam()))
                .toArray(MimeMessagePreparator[]::new);
        sendMail(preparators);
    }

    private void sendMail(final MimeMessagePreparator ...preparators) {
        try {
            mailSender.send(preparators);
        } catch (MailException e) {
            throw new MailSendException(MailErrorCode.MAIL_SEND_FAILURE);
        }
    }

    public record MailSendRequest(
            String to,
            EmailTemplate template,
            MailParameter parameter
    ) {
    }
}
