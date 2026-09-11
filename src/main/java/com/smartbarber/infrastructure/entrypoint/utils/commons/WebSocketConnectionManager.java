package com.smartbarber.infrastructure.entrypoint.utils.commons;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
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
        System.out.println("SESSION: {} -> " + orderId);
        WebSocketSession session = sessions.get(orderId);
        if(session==null || !session.isOpen()){
            System.out.println("SESSION IS CLOSED: {} -> " + orderId);
            return Mono.empty();
        }

        return session.send(
                Mono.just(session.textMessage(message))
        );
    }

    public Flux<String> receive(UUID orderId){
        System.out.println("SESSION: {} -> " + orderId);
        WebSocketSession session = sessions.get(orderId);
        if(session==null || !session.isOpen()){
            System.out.println("SESSION IS CLOSED: {} -> " + orderId);
            return Flux.empty();
        }

        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(webSocketMessage -> {
                    System.out.println("Received message: {} -> " + webSocketMessage);
                });
    }

}
