package com.smartbarber.domain.port.queue;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberQueuePort {

    Mono<BarberQueueEntity> findByEmployeeId(UUID employeeId);

    Mono<BarberQueueEntity> findByEmployeeIdForUpdate(UUID employeeId);

    Mono<BarberQueueEntity> save(BarberQueueEntity queue);

    Mono<Integer> updateAcceptingClients(
            UUID employeeId,
            boolean acceptingClients
    );
}