package com.smartbarber.domain.port.subscriptionbarbershop;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageResponsePort {
    Mono<SubscriptionBarbershopCommand> waitForResponse (UUID orderId);
    void completeResponse(UUID orderId, SubscriptionBarbershopCommand command);
}
