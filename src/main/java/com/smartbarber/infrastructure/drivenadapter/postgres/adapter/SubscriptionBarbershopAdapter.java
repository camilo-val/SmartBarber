package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.SubscriptionBarbershopData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.SubscriptionBarbershopAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Component
public class SubscriptionBarbershopAdapter implements SubscriptionBarbershopRepositoryPort {

    private final SubscriptionBarbershopData data;
    private final SubscriptionBarbershopAdapterMapper mapper;

    @Override
    public Flux<SubscriptionBarbershop> findByBarberId(UUID barberId) {
        return data.findByBarberId(barberId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<SubscriptionBarbershop> findById(UUID subscriptionId) {
        return data.findById(subscriptionId)
                .map(mapper::toDomain);

    }

    @Override
    public Mono<SubscriptionBarbershop> save(SubscriptionBarbershop subscriptionBarbershop) {
        return data.save(mapper.toEntity(subscriptionBarbershop))
                .map(mapper::toDomain);
    }
}
