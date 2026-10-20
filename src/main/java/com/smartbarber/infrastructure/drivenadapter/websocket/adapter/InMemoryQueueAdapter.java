package com.smartbarber.infrastructure.drivenadapter.websocket.adapter;

import com.smartbarber.domain.model.VirtualQueue;
import com.smartbarber.domain.model.queue.event.QueueReservation;
import com.smartbarber.domain.port.queue.QueuePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryQueueAdapter implements QueuePort {

    private final Map<String, VirtualQueue> queues =
            new ConcurrentHashMap<>();

    private VirtualQueue getOrCreate(
            String barberShopId
    ) {
        return queues.computeIfAbsent(
                barberShopId,
                id -> new VirtualQueue()
        );
    }

    @Override
    public Mono<Void> open(
            String barberShopId
    ) {

        getOrCreate(barberShopId).open();

        return Mono.empty();
    }

    @Override
    public Mono<Void> close(
            String barberShopId
    ) {

        getOrCreate(barberShopId).close();

        return Mono.empty();
    }

    @Override
    public Mono<Void> enqueue(
            String barberShopId,
            QueueReservation reservation
    ) {

        VirtualQueue queue =
                getOrCreate(barberShopId);

        if (!queue.isOpen()) {
            return Mono.error(
                    new RuntimeException(
                            "Queue is closed"
                    )
            );
        }

        queue.getReservations()
                .offer(reservation);

        return Mono.empty();
    }

    @Override
    public Mono<QueueReservation>
    nextReservation(
            String barberShopId
    ) {

        QueueReservation reservation =
                getOrCreate(barberShopId)
                        .getReservations()
                        .poll();

        return Mono.justOrEmpty(
                reservation
        );
    }

    @Override
    public Flux<QueueReservation>
    findReservations(
            String barberShopId
    ) {

        return Flux.fromIterable(
                getOrCreate(barberShopId)
                        .getReservations()
        );
    }
}