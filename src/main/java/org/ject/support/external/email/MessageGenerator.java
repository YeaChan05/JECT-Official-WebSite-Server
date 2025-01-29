package org.ject.support.external.email;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MessageGenerator {
    private final TemplateEngine templateEngine;

    public MimeMessagePreparator generateMessagePreparator(final String to,
                                                           final EmailTemplate template,
                                                           final Map<String, String> values) {
        Context context = setProperties(values);
        String content = templateEngine.process(template.getTemplateName(), context);

        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(to);
            helper.setSubject(template.getSubject());
            helper.setText(content, true);
        };
    }

    private static Context setProperties(final Map<String, String> values) {
        Context context = new Context();
        values.forEach(context::setVariable);
        return context;
    }
}
