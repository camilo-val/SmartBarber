package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SubscriptionData extends ReactiveCrudRepository<SubscriptionEntity, Integer> {
    Mono<Boolean> existsByName(String name);
}
