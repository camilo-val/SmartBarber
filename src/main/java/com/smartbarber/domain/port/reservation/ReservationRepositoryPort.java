package com.smartbarber.domain.port.reservation;

import com.smartbarber.domain.model.reservation.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public interface ReservationRepositoryPort {
    Mono<Reservation> findById(UUID reservationId);
    Mono<Boolean> existsById(UUID reservationId);
    Flux<Reservation> findByEmployeeId(UUID employeeId);
    Flux<Reservation> findByCustomerId(UUID customerId);
    Flux<Reservation> findAllBarberId(UUID barberId);
    Mono<Reservation> save(Reservation reservation);
    Mono<Boolean> existsByEmployeeAndRange(
            UUID employeeId,
            Instant startTime,
            Instant endTime);
    Mono<Boolean> existsByCustomerAndRange(
            UUID customerId,
            Instant startTime,
            Instant endTime);
}
