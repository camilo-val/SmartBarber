package com.smartbarber.infrastructure.drivenadapter.websocket.adapter;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.port.subscriptionbarbershop.MessageNotificationPort;
import com.smartbarber.domain.port.subscriptionbarbershop.MessageResponsePort;
import com.smartbarber.infrastructure.drivenadapter.websocket.config.WebSocketPaymentManager;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConnectionManager implements MessageNotificationPort{

    private final MessageResponsePort responsePort;
    private final ObjectMessageMapper objectMapper;
    private final ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
    private final WebSocketPaymentManager connectionPaymentManager;

    @Value("${payment.key}")
    private String key;

    public Mono<Void> connect() {
        UUID userId= UUID.fromString(key);

        log.info("Connecting to WebSocket server with userId: {}", userId);
        return client.execute(
                URI.create("ws://localhost:8081/ws/payment?userId=" + userId),
                session -> {
                    connectionPaymentManager.registerSession(userId, session);
                    return session.receive()
                                .doOnNext(payload -> {
                                    log.info("Received message: {}", payload.getPayloadAsText());
                                    SubscriptionBarbershopCommand command = objectMapper.readValue(
                                            payload.getPayloadAsText(), SubscriptionBarbershopCommand.class);
                                    responsePort.completeResponse(command.orderId(), command);
                                })
                            .doFinally( signalType -> connectionPaymentManager.removeSession(userId))
                            .doOnError(e -> {
                                log.error("Error in WebSocket session: {}", e.getMessage());
                            })
                            .then();
                }
        );
    }

    @Override
    public <T> Mono<Void> notification(T command) {
       log.info("id: {}, ConnectionManager.notification: {}" , command, key);
        try {
            String payload = objectMapper.writeValueAsString(command);
            return connectionPaymentManager.send(
                    UUID.fromString(key),
                    payload
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Mono.error(() -> e);
        }
    }

}
