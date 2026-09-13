package com.smartbarber.domain.port.subscriptionbarbershop;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionBarbershopRepositoryPort {
    Mono<SubscriptionBarbershop> findByOrderId(UUID id);
    Mono<Boolean> existsByOrderId(UUID orderId);
    Flux<SubscriptionBarbershop> findByBarberId(UUID barberId);
    Mono<SubscriptionBarbershop> findByTransactionId(UUID transactionId);
    Mono<SubscriptionBarbershop> save(SubscriptionBarbershop subscriptionBarbershop);
    Mono<SubscriptionBarbershop> update(SubscriptionBarbershop subscriptionBarbershop);
}
