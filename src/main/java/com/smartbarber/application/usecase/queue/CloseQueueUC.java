
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.port.queue.BarberQueuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CloseQueueUC {

    private final BarberQueuePort barberQueuePort;
    private final QueueBroadcastService queueBroadcastService;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> execute(UUID employeeId) {
        return barberQueuePort.findByEmployeeIdForUpdate(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "La cola del barbero no está configurada"
                        )
                ))
                .flatMap(queue ->
                        barberQueuePort.updateAcceptingClients(
                                employeeId,
                                false
                        )
                )
                .flatMap(rows -> rows > 0
                                ? Mono.<Void>empty()
                                : Mono.error(new IllegalStateException(
                                "No fue posible cerrar la cola"
                        ))
                )
                .as(transactionalOperator::transactional)
                .then(queueBroadcastService.publish(
                        employeeId,
                        "QUEUE_CLOSED"
                ));
    }
}
