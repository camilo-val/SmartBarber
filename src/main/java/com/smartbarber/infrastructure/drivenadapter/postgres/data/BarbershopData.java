package com.smartbarber.infrastructure.drivenadapter.postgres.data;


import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarbershopEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarbershopData extends ReactiveCrudRepository<BarbershopEntity, UUID> {
    Mono<BarbershopEntity> findByNombre(String nombre);
    Flux<BarbershopEntity> findByRazonSocial(String razonSocial);
    Mono<BarbershopEntity> findByDocumento(String documento);

}
