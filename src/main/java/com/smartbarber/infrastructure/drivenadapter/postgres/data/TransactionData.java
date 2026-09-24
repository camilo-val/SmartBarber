package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionData extends ReactiveCrudRepository<TransactionEntity, UUID> {
    Mono<TransactionEntity> findByOrderId(UUID orderId);
    Flux<TransactionEntity> findByReservationId(UUID reservationId);
    Mono<TransactionEntity> findBySubscriptionBarberId(UUID subscriptionBarberId);
    Mono<Boolean> existsByOrderId(UUID orderId);
}
