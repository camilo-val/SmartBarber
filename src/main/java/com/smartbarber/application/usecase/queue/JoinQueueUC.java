package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import com.smartbarber.domain.port.queue.QueueManagementPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class JoinQueueUC {

    private final QueueManagementPort queuePort;
    private final UserEventPublisherPort publisher;

    public Mono<Void> execute(
            String barberShopId,
            String reservationId
    ) {

        return queuePort
                .enqueue(
                        barberShopId,
                        reservationId
                )
                .doOnSuccess(unused ->
                        publisher.publish(
                                barberShopId,
                                UserEvent.builder()
                                        .type("CLIENT_JOINED")
                                        .payload(
                                                Map.of(
                                                        "reservationId",
                                                        reservationId
                                                )
                                        )
                                        .build()
                        )
                );
    }
}
