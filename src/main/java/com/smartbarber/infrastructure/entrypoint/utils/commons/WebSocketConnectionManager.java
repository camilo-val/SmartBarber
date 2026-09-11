package com.smartbarber.infrastructure.entrypoint.utils.commons;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketConnectionManager {
    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(UUID orderId,WebSocketSession session) {
        sessions.put(orderId, session);
    }

    public void removeSession(UUID orderId) {
        sessions.remove(orderId);
    }

    public Mono<Void> send(UUID orderId, String message){
        WebSocketSession session = sessions.get(orderId);
        if(session==null ||session.isOpen()){
            return Mono.empty();
        }

        return session.send(
                Mono.just(session.textMessage(message))
        );
    }

}
