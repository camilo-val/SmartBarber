package com.smartbarber.domain.port.queue.notification;

import com.smartbarber.domain.model.barberqueue.BarberQueue;
import reactor.core.publisher.Mono;

public interface NotificationEventPort {

    Mono<Void> publish(BarberQueue notification);

}