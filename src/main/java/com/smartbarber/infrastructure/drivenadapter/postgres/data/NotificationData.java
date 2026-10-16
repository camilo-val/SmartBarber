package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.NotificationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationData
        extends ReactiveCrudRepository<NotificationEntity, UUID> {

    Flux<NotificationEntity> findAllByReservationId(UUID reservationId);

    Flux<NotificationEntity> findAllByCustomerId(UUID customerId);

    Mono<Boolean> existsByTitleAndContent(String title, String content);
}
