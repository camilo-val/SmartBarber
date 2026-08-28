package com.smartbarber.infrastructure.drivenadapter.postgres.data;


import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberiaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberiaData extends ReactiveCrudRepository<BarberiaEntity, UUID> {
    Mono<BarberiaEntity> findByNombre(String nombre);
    Flux<BarberiaEntity> findByRazonSocial(String razonSocial);
    Mono<BarberiaEntity> findByDocumento(String documento);

}
