package com.smartbarber.domain.port.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import reactor.core.publisher.Flux;

public interface UserEventPublisherPort {

    void publish(
            String userId,
            UserEvent event
    );

    Flux<UserEvent> subscribe(
            String userId
    );

}