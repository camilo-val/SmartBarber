package com.smartbarber.domain.port.queue;

import reactor.core.publisher.Mono;

public interface QueueManagementPort {

    Mono<Void> open(
            String barberShopId
    );

    Mono<Void> close(
            String barberShopId
    );

    Mono<Void> enqueue(
            String barberShopId,
            String reservationId
    );

    Mono<String> nextReservation(
            String barberShopId
    );
}