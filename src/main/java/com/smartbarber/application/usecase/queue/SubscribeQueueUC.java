
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class SubscribeQueueUC {

    private final UserEventPublisherPort userEventPublisherPort;

    public Flux<UserEvent> execute(String employeeId) {
        return userEventPublisherPort.subscribe(employeeId);
    }
}
