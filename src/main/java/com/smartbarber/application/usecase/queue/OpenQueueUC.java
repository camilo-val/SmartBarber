
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OpenQueueUC {

    private final EmployeePort employeePort;
    private final BarberQueuePort barberQueuePort;
    private final QueueBroadcastService queueBroadcastService;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> execute(UUID employeeId) {
        Mono<BarberQueueEntity> operation = employeePort
                .findById(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                "No existe el empleado/barbero indicado"
                        )
                ))
                .flatMap(employee ->
                        barberQueuePort.ensureExists(
                                        employee.getId(),
                                        employee.getBarberiaId()
                                )
                                .then(barberQueuePort
                                        .findByEmployeeIdForUpdate(employeeId))
                                .switchIfEmpty(Mono.error(
                                        new IllegalStateException(
                                                "No fue posible crear o bloquear la cola del barbero"
                                        )
                                ))
                                .flatMap(locked ->
                                        barberQueuePort.updateAcceptingClients(
                                                        employeeId,
                                                        true
                                                )
                                                .flatMap(rows -> rows > 0
                                                                ? barberQueuePort.findByEmployeeId(
                                                                employeeId
                                                        )
                                                                : Mono.error(
                                                                new IllegalStateException(
                                                                        "No fue posible abrir la cola"
                                                                )
                                                        )
                                                )
                                )
                );

        return operation
                .as(transactionalOperator::transactional)
                .flatMap(queue ->
                        queueBroadcastService.publish(
                                employeeId,
                                "QUEUE_OPENED"
                        )
                );
    }
}
