package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barberqueue.BarberQueueMessageExceptions;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConsultStatusQueue {

    private final BarberQueuePort barberQueuePort;

    public Mono<BarberQueueEntity> currentStatusQueue(UUID employeeId) {
        return barberQueuePort.findByEmployeeId(employeeId)
                .switchIfEmpty(Mono.error(()
                        -> new BusinessExceptions(BarberQueueMessageExceptions.THE_QUEUE_BABER_DOES_NOT_CONFIGURATION)));
    }
}
