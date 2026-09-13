package com.smartbarber.domain.port.subscriptionbarbershop;

import com.smartbarber.application.command.MessageCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageNotificationPort {
    Mono<Void> notification(MessageCommand message);
    Flux<MessageCommand> receiveMessage(UUID orderId);
}
