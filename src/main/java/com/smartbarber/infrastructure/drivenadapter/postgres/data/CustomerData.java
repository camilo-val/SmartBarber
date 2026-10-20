package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerData extends ReactiveCrudRepository<CustomerEntity, UUID>{
    Mono<CustomerEntity> findByName(String name);
    Mono<CustomerEntity> findByDocument(String document);
    Mono<Boolean> existsByDocument(String document);
    Mono<CustomerEntity> findByUserId(UUID userId);
}
