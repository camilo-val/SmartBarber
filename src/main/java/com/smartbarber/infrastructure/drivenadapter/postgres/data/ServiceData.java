package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ServiceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ServiceData extends ReactiveCrudRepository<ServiceEntity, Integer> {

    Mono<ServiceEntity> findById(Integer id);

    Mono<ServiceEntity> findByName(String name);

    Mono<Boolean> existsByNameAndDescription(
            String name,
            String description
    );
}

