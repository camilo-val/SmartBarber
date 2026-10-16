package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ServiceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;
import reactor.core.publisher.Flux;

public interface ServiceData
        extends ReactiveCrudRepository<ServiceEntity, Integer> {

    Mono<ServiceEntity> findByName(String name);
    Flux<ServiceEntity> findAllByBarberiaId(UUID barberiaId);
    Mono<Boolean> existsByNameAndDescription(
            String name,
            String description
    );
}

