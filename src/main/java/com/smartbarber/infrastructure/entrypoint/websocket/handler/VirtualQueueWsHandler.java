
package com.smartbarber.infrastructure.entrypoint.websocket.handler;

import com.smartbarber.application.usecase.queue.QueueBroadcastService;
import com.smartbarber.application.usecase.queue.SubscribeQueueUC;
import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class VirtualQueueWsHandler implements WebSocketHandler {

    private final SubscribeQueueUC subscribeQueueUC;
    private final QueueBroadcastService queueBroadcastService;
    private final ObjectMessageMapper objectMessageMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final UUID employeeId;

        try {
            employeeId = extractEmployeeId(session);
        } catch (RuntimeException exception) {
            log.warn(
                    "Conexión WebSocket de cola rechazada: employeeId inválido"
            );

            return session.close();
        }

        log.info(
                "Cliente WebSocket suscrito a la cola del empleado {}",
                employeeId
        );

        // 1. Enviar la fotografía inicial de la cola.
        Flux<UserEvent> initialSnapshot = queueBroadcastService
                .getSnapshot(employeeId)
                .map(snapshot -> UserEvent.builder()
                        .type("QUEUE_SNAPSHOT")
                        .payload(snapshot)
                        .build())
                .flux();

        // 2. Escuchar las notificaciones posteriores.
        Flux<UserEvent> updates =
                subscribeQueueUC.execute(employeeId.toString());

        // 3. Combinar la fotografía inicial y las actualizaciones.
        Flux<String> outgoingMessages = Flux
                .merge(initialSnapshot, updates)
                .map(objectMessageMapper::writeValueAsString)
                .doOnError(error -> log.error(
                        "Error transmitiendo eventos WebSocket para employeeId {}",
                        employeeId,
                        error
                ));

        // 4. Enviar mensajes y mantener la recepción activa
        // mientras la sesión permanezca conectada.
        return session.send(
                        outgoingMessages.map(session::textMessage)
                )
                .and(session.receive().then())
                .doFinally(signal -> log.info(
                        "Conexión WebSocket cerrada para employeeId {}: {}",
                        employeeId,
                        signal
                ));
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
