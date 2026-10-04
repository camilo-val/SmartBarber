package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ServiceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ServiceData extends ReactiveCrudRepository<ServiceEntity, Integer> {

    Mono<ServiceEntity> findByName(String name);

    Mono<Boolean> existsByName(String name);
}

