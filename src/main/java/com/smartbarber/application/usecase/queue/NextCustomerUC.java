
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NextCustomerUC {

    private final BarberQueuePort barberQueuePort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final QueueBroadcastService queueBroadcastService;
    private final TransactionalOperator transactionalOperator;

    public Mono<Reservation> execute(UUID employeeId) {
        Mono<Reservation> operation = barberQueuePort
                .findByEmployeeIdForUpdate(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "La cola del barbero no está configurada"
                        )
                ))
                .flatMap(queue ->
                        reservationRepositoryPort
                                .findCurrentQueueTurn(employeeId)
                                .hasElement()
                                .flatMap(hasCurrent -> {
                                    if (hasCurrent) {
                                        return Mono.error(
                                                new IllegalStateException(
                                                        "El barbero ya tiene un turno en atención"
                                                )
                                        );
                                    }

                                    return reservationRepositoryPort
                                            .findWaitingQueueByEmployeeId(
                                                    employeeId
                                            )
                                            .next()
                                            .switchIfEmpty(Mono.error(
                                                    new IllegalStateException(
                                                            "No hay clientes esperando"
                                                    )
                                            ))
                                            .flatMap(waiting ->
                                                    reservationRepositoryPort
                                                            .save(waiting
                                                                    .iniciarAtencionDesdeCola())
                                            );
                                })
                );

        return operation
                .as(transactionalOperator::transactional)
                .flatMap(reservation ->
                        queueBroadcastService
                                .publish(employeeId, "QUEUE_UPDATED")
                                .thenReturn(reservation)
                );
    }
}
