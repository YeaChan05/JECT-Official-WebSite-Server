package org.ject.support.external.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSendServiceTest {
    @Autowired
    private EmailSendService emailSendService;

//    @Test
//    @DisplayName("send email")
//    void send_email() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        TestMailParameter parameter = new TestMailParameter("ject", "신예찬");
//        emailSendService.sendEmail("qkenrdl05@gmail.com", EmailTemplate.ACCEPTANCE, parameter);
//        stopWatch.stop();
//        System.out.println(stopWatch.getTotalTimeSeconds());
//    }

    @Getter
    @AllArgsConstructor
    static class TestMailParameter extends MailParameter {
        private final String to;
        private final String value;
    }
}
