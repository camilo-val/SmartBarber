package com.smartbarber.domain.port.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import reactor.core.publisher.Flux;

public interface QueueSubscriptionPort {

    Flux<UserEvent> subscribe(
            String barberShopId
    );

}