package com.smartbarber.infrastructure.entrypoint.utils.commons;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class WebSocketConnectionManager {
    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(UUID orderId, WebSocketSession session) {
        log.info("Registering WebSocket session for orderId: {},  sessionID: {}", orderId,session.getId());
        sessions.put(orderId, session);
    }

    public Mono<Void> send(UUID orderId, String message){
        WebSocketSession session = sessions.get(orderId);

        if(session==null){
            log.warn("WebSocket session for orderId: {} not found", orderId);
            return Mono.empty();
        }
        log.info("Sending WebSocket message for orderId: {},  sessionID: {}", orderId,session.getId());
        return session.send(Mono.just(session.textMessage(message)));
    }

    public void removeSession(UUID orderId) {
        sessions.remove(orderId);
    }

}
