package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.EmployeeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeData extends ReactiveCrudRepository<EmployeeEntity, UUID>{
    Mono<EmployeeEntity> findByNombre(String nombre);
    Mono<EmployeeEntity> findByDocumento(String documento);

}
