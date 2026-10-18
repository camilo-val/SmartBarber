/*package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import com.smartbarber.domain.port.queue.QueueManagementPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
//@Component
public class OpenQueueUC {

    private final QueueManagementPort queuePort;
    private final UserEventPublisherPort publisher;

    public Mono<Void> execute(
            String barberShopId
    ) {

        return queuePort
                .open(barberShopId)
                .doOnSuccess(unused ->
                        publisher.publish(
                                barberShopId,
                                UserEvent.builder()
                                        .type("QUEUE_OPENED")
                                        .build()
                        )
                );
    }
}*/