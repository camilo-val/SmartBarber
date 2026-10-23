
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.model.queue.QueueSnapshot;
import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueBroadcastService {

    private final BarberQueuePort barberQueuePort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final UserEventPublisherPort userEventPublisherPort;

    public Mono<QueueSnapshot> getSnapshot(UUID employeeId) {
        return barberQueuePort.findByEmployeeId(employeeId)
                .switchIfEmpty(Mono.error(
                    new IllegalStateException(
                            "La cola del barbero no está configurada"
                    )
                ))
                .flatMap(queue -> Boolean.TRUE.equals(queue.getAcceptingClients())
                        ? this.buildSnapshot(queue)
                        : Mono.error(() -> new IllegalStateException("La cola del barbero no está aceptando clientes")));
    }

    private Mono<QueueSnapshot> getSnapshots(UUID employeeId) {
        return barberQueuePort.findByEmployeeId(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "La cola del barbero no está configurada"
                        )
                ))
                .flatMap(this::buildSnapshot);
    }

    public Mono<Void> publish(UUID employeeId, String eventType) {
        return getSnapshots(employeeId)
                .doOnNext(snapshot ->
                        userEventPublisherPort.publish(
                                employeeId.toString(),
                                UserEvent.builder()
                                        .type(eventType)
                                        .payload(snapshot)
                                        .build()
                        )
                )
                .then();
    }

    private Mono<QueueSnapshot> buildSnapshot(BarberQueueEntity queue) {
        UUID employeeId = queue.getEmployeeId();

        Mono<Optional<Reservation>> currentMono =
                reservationRepositoryPort.findCurrentQueueTurn(employeeId)
                        .map(Optional::of)
                        .defaultIfEmpty(Optional.empty());

        Mono<List<Reservation>> waitingMono =
                reservationRepositoryPort
                        .findWaitingQueueByEmployeeId(employeeId)
                        .collectList();

        return Mono.zip(currentMono, waitingMono)
                .map(tuple -> {
                    Reservation current = tuple.getT1().orElse(null);
                    List<Reservation> waiting = tuple.getT2();

                    int remainingMinutes =
                            estimateRemainingMinutes(current);

                    QueueSnapshot.QueueTurn currentTurn = current == null
                            ? null
                            : new QueueSnapshot.QueueTurn(
                            current.getId(),
                            current.getCustomerId(),
                            0,
                            0,
                            current.getDuration(),
                            current.getStatus().name()
                    );

                    int accumulatedMinutes = remainingMinutes;

                    List<QueueSnapshot.QueueTurn> turns =
                            new ArrayList<>();

                    for (int i = 0; i < waiting.size(); i++) {
                        Reservation reservation = waiting.get(i);

                        turns.add(new QueueSnapshot.QueueTurn(
                                reservation.getId(),
                                reservation.getCustomerId(),
                                i + 1,
                                accumulatedMinutes,
                                reservation.getDuration(),
                                reservation.getStatus().name()
                        ));

                        if (reservation.getDuration() != null) {
                            accumulatedMinutes +=
                                    reservation.getDuration();
                        }
                    }

                    return new QueueSnapshot(
                            employeeId,
                            Boolean.TRUE.equals(
                                    queue.getAcceptingClients()
                            ),
                            currentTurn,
                            List.copyOf(turns),
                            OffsetDateTime.now()
                    );
                });
    }

    private int estimateRemainingMinutes(Reservation current) {
        if (current == null || current.getDuration() == null) {
            return 0;
        }

        if (current.getStartTime() == null) {
            return current.getDuration();
        }

        long elapsed = Duration.between(
                current.getStartTime(),
                OffsetDateTime.now()
        ).toMinutes();

        return Math.max(
                0,
                current.getDuration() - (int) elapsed
        );
    }
}
