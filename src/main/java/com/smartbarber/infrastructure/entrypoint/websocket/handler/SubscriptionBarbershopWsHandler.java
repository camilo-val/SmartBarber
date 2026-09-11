package com.smartbarber.infrastructure.entrypoint.websocket.handler;

import com.smartbarber.application.command.SubscriptionBarbershopCommand;
import com.smartbarber.application.usecase.subscriptionbarber.CreateSubscriptionBarberUC;
import com.smartbarber.application.usecase.subscriptionbarber.SendMessageUC;
import com.smartbarber.infrastructure.entrypoint.utils.commons.WebSocketConnectionManager;
import com.smartbarber.infrastructure.entrypoint.websocket.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionBarbershopWsHandler implements WebSocketHandler {

    private final WebSocketConnectionManager connectionManager;
    private final ObjectMapper objectMapper;
    private final CreateSubscriptionBarberUC createSubscriptionBarberUC;
    private final SendMessageUC sendMessageUC;
    private final MessageMapper mapper;

    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(body -> this.deserialize(body)
                        .map(subscription -> {
                            connectionManager.registerSession(subscription.orderId(),session);
                            return subscription;
                        }))
                .flatMap(createSubscriptionBarberUC::createSubscription)
                .map(mapper::toRequest)
                .flatMap(sendMessageUC::sendMessage)
                .doOnNext(message -> log.info("message -> {}", message))
                .then();
    }

    private Mono<SubscriptionBarbershopCommand> deserialize(String message) {
        try {
            return Mono.just(objectMapper.readValue(message, SubscriptionBarbershopCommand.class));
        }catch (Exception ex){
            return Mono.error(ex);
        }
    }
}
