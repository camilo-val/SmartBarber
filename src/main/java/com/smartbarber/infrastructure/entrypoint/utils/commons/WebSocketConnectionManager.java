package com.smartbarber.infrastructure.entrypoint.utils.commons;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class WebSocketConnectionManager {
    private final Map<UUID, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(UUID userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public Mono<Void> send(UUID userId, String message){
        WebSocketSession session = sessions.get(userId);

        if(session==null){
            return Mono.empty();
        }
        return session.send(Mono.just(session.textMessage(message)));
    }


    public void removeSession(UUID userId) {
        sessions.remove(userId);
    }

}
