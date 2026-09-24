package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionBarbershopEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionBarbershopData extends ReactiveCrudRepository<SubscriptionBarbershopEntity, UUID> {
    Flux<SubscriptionBarbershopEntity> findByBarberId(UUID barberId);
}
