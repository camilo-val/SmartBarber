
package com.smartbarber.infrastructure.entrypoint.websocket.handler;

import com.smartbarber.application.usecase.queue.SubscribeQueueUC;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class VirtualQueueWsHandler implements WebSocketHandler {

    private final SubscribeQueueUC subscribeQueueUC;
    private final ObjectMessageMapper objectMessageMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        UUID employeeId;

        try {
            employeeId = extractEmployeeId(session);
        } catch (RuntimeException exception) {
            log.warn(
                    "Conexión WebSocket de cola rechazada: employeeId inválido"
            );
            return session.close();
        }

        return session.send(
                subscribeQueueUC.execute(employeeId.toString())
                        .map(objectMessageMapper::writeValueAsString)
                        .map(session::textMessage)
        );
    }

    private UUID extractEmployeeId(WebSocketSession session) {
        String employeeId = UriComponentsBuilder
                .fromUri(session.getHandshakeInfo().getUri())
                .build()
                .getQueryParams()
                .getFirst("employeeId");

        if (employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException(
                    "employeeId es obligatorio"
            );
        }

        return UUID.fromString(employeeId);
    }
}
