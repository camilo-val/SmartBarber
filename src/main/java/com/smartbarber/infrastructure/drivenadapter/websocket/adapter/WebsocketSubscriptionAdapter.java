package com.smartbarber.infrastructure.drivenadapter.websocket.adapter;

import com.smartbarber.application.command.MessageCommand;
import com.smartbarber.application.port.subscriptionbarbershop.MessageNotificationPort;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import com.smartbarber.infrastructure.entrypoint.utils.commons.WebSocketConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Component
public class WebsocketSubscriptionAdapter implements MessageNotificationPort{

    private final ObjectMessageMapper objectMapper;
    private final ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
    private final WebSocketConnectionManager connectionManager;

    public WebsocketSubscriptionAdapter (ObjectMessageMapper objectMapper, WebSocketConnectionManager connectionManager) {
        this.objectMapper = objectMapper;
        this.connectionManager = connectionManager;
    }

    @Value("${payment.key}")
    private String key;

    public Mono<Void> connect() {
        UUID userId= UUID.fromString(key);

        System.out.println("Connecting to WebSocket server with userId: " + userId);
        return client.execute(
                URI.create("ws://localhost:8081/ws/payment?userId=" + userId),
                session -> {
                    System.out.println("CONNECT {} -> " + session.getId());
                    connectionManager.registerSession(userId, session);
                    return session.receive()
                            .doFinally( signalType -> connectionManager.removeSession(userId))
                            .doOnError(e -> {
                                System.out.println("ERROR WS");
                                e.printStackTrace();
                            })
                            .then();
                }
        );
    }

    @Override
    public Mono<Void> notification(MessageCommand command) {
        System.out.println("Adapter {} -> " +command);
        try {
            String payload = objectMapper.writeValueAsString(command);
            return connectionManager.send(
                    UUID.fromString(key),
                    payload
            ).then();

        } catch (Exception e) {
            e.printStackTrace();
            return Mono.error(() -> e);
        }
    }

    @Override
    public Flux<MessageCommand> receiveMessage(UUID orderId) {
        try {
            return connectionManager.receive(orderId)
                    .map(message -> objectMapper.readValue(message, MessageCommand.class));
        }catch (Exception e){
            return Flux.error(e);
        }
    }


}
