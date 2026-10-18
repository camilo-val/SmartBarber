/*package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import com.smartbarber.domain.port.queue.QueueManagementPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
//@Component
public class NextCustomerUC {

    private final QueueManagementPort queuePort;
    private final UserEventPublisherPort publisher;

    public Mono<Void> execute(
            String barberShopId
    ) {

        return queuePort
                .nextReservation(
                        barberShopId
                )
                .flatMap(reservationId -> {

                    publisher.publish(
                            barberShopId,
                            UserEvent.builder()
                                    .type("YOUR_TURN")
                                    .payload(
                                            Map.of(
                                                    "reservationId",
                                                    reservationId
                                            )
                                    )
                                    .build()
                    );

                    return Mono.empty();
                });
    }
}*/