package com.smartbarber.infrastructure.drivenadapter.websocket.adapter;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserBroker implements UserEventPublisherPort {

    private final Map<String, Sinks.Many<UserEvent>> queues =
            new ConcurrentHashMap<>();

    private Sinks.Many<UserEvent> getOrCreate(
            String barbershopId
    ) {

        return queues.computeIfAbsent(
                barbershopId,
                id -> Sinks.many()
                        .multicast()
                        .onBackpressureBuffer()
        );
    }

    @Override
    public void publish(
            String barbershopId,
            UserEvent event
    ) {

        getOrCreate(barbershopId)
                .tryEmitNext(event);
    }

    public Flux<UserEvent> subscribe(
            String barbershopId
    ) {

        return getOrCreate(barbershopId)
                .asFlux();
    }

}