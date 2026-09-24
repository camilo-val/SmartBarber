package com.smartbarber.domain.port.event;

import com.smartbarber.application.command.in.TransactionCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageResponsePort {
    Mono<TransactionCommand> waitForResponse (UUID orderId);
    <T> void completeResponse(UUID orderId, T command);
}
