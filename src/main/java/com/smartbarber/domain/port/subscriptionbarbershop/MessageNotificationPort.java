package com.smartbarber.domain.port.subscriptionbarbershop;

import com.smartbarber.application.command.out.MessageCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageNotificationPort {
    <T> Mono<Void> notification(T message);
}
