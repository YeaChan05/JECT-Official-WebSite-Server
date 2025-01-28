package org.ject.support.external.email;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MailParameterTest {

    @Test
    @DisplayName("init param test")
    void mail_param_init_test() {
        TestMailParameter parameter = new TestMailParameter("test", 1, true);
        Map<String, ?> param = parameter.getParam();
        assertThat(param.get("test")).isEqualTo("test");
        assertThat(param.get("testInt")).isEqualTo("1");
        assertThat(param.get("testBool")).isEqualTo("true");

    }

    @Getter
    @AllArgsConstructor
    static class TestMailParameter extends MailParameter {
        private String test;
        private int testInt;
        private boolean testBool;
    }
}
