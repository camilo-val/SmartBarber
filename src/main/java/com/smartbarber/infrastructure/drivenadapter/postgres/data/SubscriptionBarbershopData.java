package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionBarbershopEntyty;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionBarbershopData extends ReactiveCrudRepository<SubscriptionBarbershopEntyty, UUID> {
    Mono<SubscriptionBarbershopEntyty> findByOrderId(UUID orderId);
    Flux<SubscriptionBarbershopEntyty> findByBarberId(UUID barberId);
    Mono<Boolean> existsByOrderId(UUID orderId);
    Mono<SubscriptionBarbershopEntyty> findByTransactionId(UUID transactionId);


}
