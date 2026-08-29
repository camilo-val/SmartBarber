package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.barberia;

import com.smartbarber.domain.exceptions.BarberiaExcepciones;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.error.ErrorRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.ExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.MensajesExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant;
import lombok.AllArgsConstructor;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.UNEXPECTED;

@Component
@Order(-2)
@AllArgsConstructor
public class GlobalHandlerError implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = getStatus(ex);
        Map<String,ErrorRsDto> error = errorMap(ex);
        exchange.getResponse().setStatusCode(status);

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return Mono.fromCallable(() -> objectMapper.writeValueAsBytes(error))
                .flatMap(bytes -> {
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                });
    }

    private HttpStatus getStatus(Throwable ex) {
        if (ex instanceof BarberiaExcepciones) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof ExcepcionesTecnicas excepcionesTecnicas) {
            return switch (excepcionesTecnicas.getMensajesExcepciones()) {
                case BAD_REQUEST ->
                        HttpStatus.BAD_REQUEST;
                case INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR ->
                        HttpStatus.INTERNAL_SERVER_ERROR;
                default ->
                        HttpStatus.SERVICE_UNAVAILABLE;
            };
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Map<String, ErrorRsDto> errorMap(Throwable ex) {
        if (ex instanceof BarberiaExcepciones barberiaExcepciones) {
            return buildMapError(barberiaExcepciones.getMensajesExcepcionBarberia().getCodigo()
                    ,barberiaExcepciones.getMensajesExcepcionBarberia().getMensaje(),
                    HandlerConstant.BUSINESS
            );

        } else if (ex instanceof ExcepcionesTecnicas excepcionesTecnicas) {
            return buildMapError(excepcionesTecnicas.getMensajesExcepciones().getCode()
                    ,excepcionesTecnicas.getMensajesExcepciones().getMensaje(),
                    HandlerConstant.TECHNICAL
            );

        }
        return buildMapError(MensajesExcepcionesTecnicas.UNEXPECTED_ERROR.getCode()
                ,MensajesExcepcionesTecnicas.UNEXPECTED_ERROR.getMensaje(),
                UNEXPECTED);


    }
    private Map<String, ErrorRsDto> buildMapError(String code, String message, String reason) {
        ErrorRsDto error = ErrorRsDto.builder()
                .reason(reason)
                .message(message)
                .code(code)
                .date(Instant.now())
                .build();

        return Map.of("data", error);
    }

}
