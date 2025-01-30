package org.ject.support.external.email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/**
 * 메일 전송시 필요한 파라미터를 정의하는 추상 클래스
 * <br>
 * jackson을 사용하기에 getter를 가지거나 record여야 합니다
 */
public class MailParameter {

    @JsonIgnore
    public Map<String, String> getParam() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = toJson(objectMapper);
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new MailSendException(MailErrorCode.MAIL_PARAMETER_PARSE_FAILURE);
        }
    }

    private String toJson(final ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}

