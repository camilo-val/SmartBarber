package com.smartbarber.infrastructure.entrypoint.websocket.handler;
/*
import com.smartbarber.application.usecase.subscriptionbarber.ExecuteSubscriptionBarberUC;
import com.smartbarber.application.usecase.subscriptionbarber.SendMessageUC;
import com.smartbarber.application.usecase.transaction.WaitMessageResponseUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.subscriptionbarber.SubscriptionBarberEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.WebSocketExceptions;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import com.smartbarber.infrastructure.entrypoint.utils.commons.WebSocketConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionBarbershopWsHandler implements WebSocketHandler {
    private final WebSocketConnectionManager connectionManager;
    private final ObjectMessageMapper objectMapper;
    private final ExecuteSubscriptionBarberUC executeUc;
    private final WaitMessageResponseUC waitMessageResponseUC;
    private final SendMessageUC sendMessageUC;
    private final SubscriptionBarberEntryMapper mapper;
    private final WebSocketExceptions exceptions;
    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        UUID userId = extractUserId(session);
        connectionManager.registerSession(userId, session);

       return session.receive()
               .map(WebSocketMessage::getPayloadAsText)
               .doOnNext(body -> log.info("SubscriptionBarbershopWsHandler.handle: {}", body))
               .flatMap(body ->{

                   return Mono.just(
                                objectMapper.buildMessage(body)
                        );
                       }
                   )
               .doOnError(error -> log.error("Error in SubscriptionBarbershopWsHandler.handle: {}", error.getMessage(), error))
               .flatMap( requestBody -> {
                   log.info("after mapper {}", requestBody);
                   return executeUc.execute(mapper.toRequest(requestBody))
                           .flatMap(eventMessage ->
                                   sendMessageUC.sendMessage(mapper.toRequest(eventMessage)).thenReturn(eventMessage.getOrderId())
                           )
                           .flatMap(waitMessageResponseUC::waitForResponseAndSendMessage)
                           .doOnNext(responseEvent -> log.info("SubscriptionBarbershopWsHandler.handle: responseEvent: {}", responseEvent))
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

    private UUID extractUserId(WebSocketSession session) {
        String userId = UriComponentsBuilder
                .fromUri(session.getHandshakeInfo().getUri())
                .build()
                .getQueryParams()
                .getFirst("userId");
        return UUID.fromString(userId);
    }
}*/
