package com.smartbarber.infrastructure.entrypoint.websocket.handler.notification;

import com.smartbarber.infrastructure.drivenadapter.websocket.adapter.NotificationEventAdapter;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NotificationWebSocketHandler implements WebSocketHandler {

    private final NotificationEventAdapter adapter;
    private final ObjectMessageMapper objectMapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println("NotificationWebSocketHandler.handle: " + session.getId());
        return session.send(
                adapter.getSink()
                        .asFlux()
                        .map(objectMapper::writeValueAsString)
                        .map(session::textMessage)
        );
    }
}