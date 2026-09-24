package com.smartbarber.domain.port.subscriptionbarbershop;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionBarbershopRepositoryPort {
    Flux<SubscriptionBarbershop> findByBarberId(UUID barberId);
    Mono<SubscriptionBarbershop> findById(UUID subscriptionId);
    Mono<SubscriptionBarbershop> save(SubscriptionBarbershop subscriptionBarbershop);
}
