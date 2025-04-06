package org.ject.support.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.ject.support.common.exception.GlobalErrorCode;
import org.ject.support.common.exception.GlobalException;

import java.util.List;


public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final TypeReference<List<String>> LIST_TYPE_REFERENCE = new TypeReference<>() {};

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new GlobalException(GlobalErrorCode.JSON_MARSHALLING_FAILURE);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, LIST_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            throw new GlobalException(GlobalErrorCode.JSON_MARSHALLING_FAILURE);
        }
    }
}
