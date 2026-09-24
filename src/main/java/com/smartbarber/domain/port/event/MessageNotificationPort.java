package com.smartbarber.domain.port.event;

import reactor.core.publisher.Mono;

public interface MessageNotificationPort {
    <T> Mono<Void> notification(T message);
}
