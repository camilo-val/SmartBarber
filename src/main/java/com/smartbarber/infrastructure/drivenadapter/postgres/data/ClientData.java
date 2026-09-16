package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientData extends ReactiveCrudRepository<ClientEntity, UUID>{
    Mono<ClientEntity> findByName(String name);
    Mono<ClientEntity> findByDocument(String document);
    Mono<Boolean> existsByDocument(String document);
}
