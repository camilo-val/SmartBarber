package com.smartbarber.infrastructure.entrypoint.websocket.handler;

import com.smartbarber.application.usecase.subscriptionbarber.CreateSubscriptionBarberUC;
import com.smartbarber.application.usecase.subscriptionbarber.SendMessageUC;
import com.smartbarber.application.usecase.subscriptionbarber.WaitMessageResponseUC;
import com.smartbarber.infrastructure.entrypoint.utils.WebSocketExceptions;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import com.smartbarber.infrastructure.entrypoint.utils.commons.WebSocketConnectionManager;
import com.smartbarber.infrastructure.entrypoint.websocket.MessageMapper;
import com.smartbarber.infrastructure.entrypoint.websocket.dto.MessageRqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionBarbershopWsHandler implements WebSocketHandler {

    private final WebSocketConnectionManager connectionManager;
    private final ObjectMessageMapper objectMapper;
    private final CreateSubscriptionBarberUC createSubscriptionBarberUC;
    private final WaitMessageResponseUC waitMessageResponseUC;
    private final SendMessageUC sendMessageUC;
    private final MessageMapper mapper;
    private final WebSocketExceptions exceptions;

    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

       return session.receive()
               .map(WebSocketMessage::getPayloadAsText)
               .doOnNext(body -> log.info("SubscriptionBarbershopWsHandler.handle: {}", body))
               .flatMap(body ->
                       Mono.just(
                               objectMapper.readValue(body, MessageRqDto.class)
                       ))
               .flatMap( requestBody -> {
                   connectionManager.registerSession(requestBody.data().orderId(),session);
                   return createSubscriptionBarberUC.createSubscription(requestBody.data())
                           .flatMap(eventMessage ->
                                   sendMessageUC.sendMessage(mapper.toRequest(eventMessage)).thenReturn(eventMessage.getOrderId())
                           )
                           .flatMap(waitMessageResponseUC::waitForResponseAndSendMessage)
                           .flatMap(responseEvent ->
                                   connectionManager.send(responseEvent.getOrderId(),
                                           objectMapper.writeValueAsString(responseEvent))
                           ).onErrorResume(error->{
                               log.info("Error in SubscriptionBarbershopWsHandler.handle: {}", error.getMessage(), error);
                                        return connectionManager.send(requestBody.data().orderId(),
                                       objectMapper.writeValueAsString(exceptions.mapError(error)));}
                            );
               })
               .then();
    }
}
