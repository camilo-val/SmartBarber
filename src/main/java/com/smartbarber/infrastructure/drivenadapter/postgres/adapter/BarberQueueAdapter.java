
package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.BarberQueueData;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BarberQueueAdapter implements BarberQueuePort {

    private final BarberQueueData data;

    @Override
    public Mono<BarberQueueEntity> findByEmployeeId(UUID employeeId) {
        return data.findByEmployeeId(employeeId);
    }

    @Override
    public Mono<BarberQueueEntity> findByEmployeeIdForUpdate(UUID employeeId) {
        return data.findByEmployeeIdForUpdate(employeeId);
    }

    @Override
    public Mono<BarberQueueEntity> save(BarberQueueEntity queue) {
        return data.save(queue);
    }

    @Override
    public Mono<Void> ensureExists(UUID employeeId, UUID barbershopId) {
        return data.insertIfAbsent(employeeId, barbershopId).then();
    }

    @Override
    public Mono<Integer> updateAcceptingClients(
            UUID employeeId,
            boolean acceptingClients
    ) {
        return data.updateAcceptingClients(
                employeeId,
                acceptingClients
        );
    }
}
