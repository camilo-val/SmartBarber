package com.smartbarber.domain.port;

import com.smartbarber.domain.model.notification.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationPort {

    Mono<Notification> findById(UUID id);

    Mono<Notification> save(Notification notification);

    Mono<Notification> update(UUID id, Notification notification);

    Flux<Notification> findByIdCustomer(UUID customerId);

    Flux<Notification> findByIdReservation(UUID reservationId);

    Mono<Boolean> existByTitleAndContent(String title, String content);
}
