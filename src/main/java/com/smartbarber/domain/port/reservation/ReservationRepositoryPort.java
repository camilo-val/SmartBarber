
package com.smartbarber.domain.port.reservation;

import com.smartbarber.domain.model.reservation.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ReservationRepositoryPort {

    Mono<Reservation> findById(UUID id);

    Mono<Boolean> existsById(UUID id);

    Flux<Reservation> findByEmployeeId(UUID employeeId);

    Flux<Reservation> findByCustomerId(UUID customerId);

    Flux<Reservation> findAllBarberId(UUID barberId);

    Mono<Reservation> save(Reservation reservation);

    Mono<Boolean> existsByEmployeeAndRange(
            UUID employeeId,
            OffsetDateTime startTime,
            OffsetDateTime endTime
    );

    Mono<Boolean> existsByCustomerAndRange(
            UUID customerId,
            OffsetDateTime startTime,
            OffsetDateTime endTime
    );

    Flux<Reservation> findWaitingQueueByEmployeeId(UUID employeeId);

    Mono<Reservation> findCurrentQueueTurn(UUID employeeId);

    Mono<Long> countPreviousWaitingTurns(
            UUID employeeId,
            OffsetDateTime entryTime,
            UUID reservationId
    );

    Mono<OffsetDateTime> findNextScheduledReservationStart(
            UUID employeeId,
            OffsetDateTime from
    );

    Mono<Integer> calculateRemainingQueueDuration(
            UUID employeeId,
            OffsetDateTime currentTime
    );
}
