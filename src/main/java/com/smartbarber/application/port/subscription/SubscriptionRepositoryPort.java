package com.smartbarber.application.port.subscription;

import com.smartbarber.domain.model.suscription.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionRepositoryPort {
    Mono<Subscription> save(Subscription subscription);
    Mono<Subscription> findById(Integer id);
    Flux<Subscription> findAll();
    Mono <Boolean> existsById(Integer id);
}
