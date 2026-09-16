package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.subscription.SubscriptionRepositoryPort;
import com.smartbarber.domain.model.suscription.Subscription;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.SubscriptionData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.SubscriptionAdapterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class SubscriptionAdapter implements SubscriptionRepositoryPort {
    private final SubscriptionData data;
    private final SubscriptionAdapterMapper mapper;
    @Override
    public Mono<Subscription> save(Subscription subscription) {
        return data.save(mapper.toEntity(subscription))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Subscription> findAll() {
        return data.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Subscription> findById(Integer id) {
        return data.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return data.existsByName(name);
    }



}
