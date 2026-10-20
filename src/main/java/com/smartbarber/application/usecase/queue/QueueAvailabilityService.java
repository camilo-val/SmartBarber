package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QueueAvailabilityService {

    private final ReservationRepositoryPort reservationRepositoryPort;

    public Mono<Void> validateAvailability(
            UUID employeeId,
            Integer newDuration
    ) {

        if (employeeId == null) {
            return Mono.error(
                    new IllegalArgumentException(
                            "El id del barbero es obligatorio"
                    )
            );
        }

        if (newDuration == null || newDuration <= 0) {
            return Mono.error(
                    new IllegalArgumentException(
                            "La duración debe ser mayor a cero"
                    )
            );
        }

        return Mono.defer(() -> {

            Instant now = Instant.now();

            Mono<Integer> queueDurationMono =
                    reservationRepositoryPort
                            .calculateRemainingQueueDuration(
                                    employeeId,
                                    now
                            )
                            .defaultIfEmpty(0);

            Mono<Instant> nextReservationMono =
                    reservationRepositoryPort
                            .findNextScheduledReservationStart(
                                    employeeId,
                                    now
                            );

            return Mono.zip(
                            queueDurationMono,
                            nextReservationMono.defaultIfEmpty(Instant.MAX)
                    )
                    .flatMap(tuple -> {

                        int pendingMinutes = tuple.getT1();
                        Instant nextReservationStart = tuple.getT2();

                        Instant estimatedFinish =
                                now.plus(
                                        Duration.ofMinutes(
                                                (long) pendingMinutes
                                                        + newDuration
                                        )
                                );

                        if (!estimatedFinish.isBefore(nextReservationStart)) {
                            return Mono.error(
                                    new IllegalStateException(
                                            "No hay tiempo suficiente para "
                                                    + "atender este servicio "
                                                    + "antes de la siguiente "
                                                    + "reserva agendada"
                                    )
                            );
                        }

                        return Mono.empty();
                    });
        });
    }
}