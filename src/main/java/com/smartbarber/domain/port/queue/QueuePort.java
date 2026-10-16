package com.smartbarber.domain.port.queue;

import com.smartbarber.domain.model.queue.event.QueueReservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QueuePort {

    Mono<Void> open(
            String barberShopId
    );

    Mono<Void> close(
            String barberShopId
    );

    Mono<Void> enqueue(
            String barberShopId,
            QueueReservation reservation
    );

    Mono<QueueReservation> nextReservation(
            String barberShopId
    );

    Flux<QueueReservation> findReservations(
            String barberShopId
    );

}