package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.QueueSubscriptionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class SubscribeQueueUC {

    private final QueueSubscriptionPort port;

    public Flux<UserEvent> execute(
            String barberShopId
    ) {

        return port.subscribe(
                barberShopId
        );
    }

}
