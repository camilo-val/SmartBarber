package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.MessageNotificationPort;
import com.smartbarber.domain.port.subscriptionbarbershop.MessageResponsePort;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class WaitMessageResponseUC {
    private final MessageResponsePort responsePort;
    private final SubscriptionBarbershopRepositoryPort repositoryPort;

    public Mono<SubscriptionBarbershop> waitForResponseAndSendMessage(UUID orderId) {
        log.info("Waiting for response and sending message for orderId: {}", orderId);
        return responsePort.waitForResponse(orderId)
                .map(e -> {
                    log.info("Received response for orderId {}: {}", orderId, e);
                    return e;
                })
            .flatMap(subscriptionMessage ->
                repositoryPort.findByOrderId(subscriptionMessage.orderId())
                    .flatMap(subscription -> {
                        log.info("Updating subscription for orderId {}: {}", subscriptionMessage.orderId(), subscriptionMessage.status());
                        SubscriptionBarbershop update = subscription.update(subscriptionMessage.status());
                        return repositoryPort.update(update);
                    }
            ))
            .onErrorResume(e -> Mono.error(() -> new TechnicalExceptions(TechnicalMessageExceptions.TIME_OUT)));
    }
}