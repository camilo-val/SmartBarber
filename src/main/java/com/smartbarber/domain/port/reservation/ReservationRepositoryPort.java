
package com.smartbarber.domain.port.reservation;

import com.smartbarber.domain.model.reservation.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
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
            Instant startTime,
            Instant endTime
    );

    Mono<Boolean> existsByCustomerAndRange(
            UUID customerId,
            Instant startTime,
            Instant endTime
    );

    Flux<Reservation> findWaitingQueueByEmployeeId(UUID employeeId);

    Mono<Reservation> findCurrentQueueTurn(UUID employeeId);

    Mono<Long> countPreviousWaitingTurns(
            UUID employeeId,
            Instant entryTime,
            UUID reservationId
    );

    Mono<Instant> findNextScheduledReservationStart(
            UUID employeeId,
            Instant from
    );

    Mono<Integer> calculateRemainingQueueDuration(
            UUID employeeId,
            Instant currentTime
    );
}
