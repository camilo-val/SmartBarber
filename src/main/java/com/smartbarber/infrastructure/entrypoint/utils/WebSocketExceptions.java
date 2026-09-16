package com.smartbarber.infrastructure.entrypoint.utils;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.error.ErrorRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.websocket.dto.MessageRsDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.*;

@Component
public class WebSocketExceptions {
    public MessageRsDto mapError(Throwable error){
        if (error instanceof BusinessExceptions businessExceptions){
            return buildMessageError(businessExceptions.getExceptionMessage().getCode()
                    , businessExceptions.getMessage(),BUSINESS);
        }else if( error instanceof TechnicalExceptions technicalMessageExceptions){
            return buildMessageError(technicalMessageExceptions.getMensajesExcepciones().getCode(),
                    technicalMessageExceptions.getMessage(),
                    TECHNICAL);
        }
        return buildMessageError(TechnicalMessageExceptions.UNEXPECTED_ERROR.getCode(),
                error.getMessage(),
                TECHNICAL);
    }

    private MessageRsDto buildMessageError(String code, String message, String reason){
        return MessageRsDto.builder()
                .type(ERROR)
                .data(ErrorRsDto.builder().reason(reason)
                        .code(code)
                        .message(message)
                        .date(Instant.now())
                        .build())
                .build();
    }
}
