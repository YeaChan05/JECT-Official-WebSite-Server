package org.ject.support.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Json2MapSerializer.class, ObjectMapper.class})
class Json2MapSerializerTest {
    @Autowired
    Json2MapSerializer json2MapSerializer;

    @Test
    @DisplayName("init param test")
    void mail_param_init_test() {
        TestMailParameter parameter = new TestMailParameter("test", 1, true);
        Map<String, ?> param = json2MapSerializer.serializeAsMap(parameter);
        assertThat(param.get("test")).isEqualTo("test");
        assertThat(param.get("testInt")).isEqualTo("1");
        assertThat(param.get("testBool")).isEqualTo("true");

    }

    @Getter
    @AllArgsConstructor
    static class TestMailParameter {
        private String test;
        private int testInt;
        private boolean testBool;
    }
}
