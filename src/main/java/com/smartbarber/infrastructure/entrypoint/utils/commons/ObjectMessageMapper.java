package com.smartbarber.infrastructure.entrypoint.utils.commons;

import com.smartbarber.domain.enums.TransactionType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.transaction.TransactionMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.websocket.dto.event.MessageRqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class ObjectMessageMapper {
    private final ObjectMapper objectMapper;

    public <T> T readValue(String message, Class<T> clazz) {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode readTree(String message) {
        try {
            return objectMapper.readTree(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MessageRqDto<?> buildMessage(String message){
        return switch (TransactionType.valueOf(readTree(message).get("type").asString())) {
            case SUBSCRIPTION_TRANSACTION -> readValue(message, MessageRqDto.class);
            default -> throw new BusinessExceptions(TransactionMessageExceptions.UNSUPPORTED_TRANSACTION_TYPE);
        };
    }
}
