package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ReservationData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ReservationAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationRepositoryPort {

    private final ReservationData data;
    private final ReservationAdapterMapper mapper;

    @Override
    public Mono<Reservation> findById(UUID id) {
        return data.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return data.existsById(id);
    }

    @Override
    public Flux<Reservation> findByEmployeeId(UUID employeeId) {
        return data.findByEmployeeId(employeeId).map(mapper::toDomain);
    }

    @Override
    public Flux<Reservation> findByCustomerId(UUID customerId) {
        return data.findByCustomerId(customerId).map(mapper::toDomain);
    }

    @Override
    public Flux<Reservation> findAllBarberId(UUID barberId) {
        // Ajusta esta consulta a las relaciones reales de tu esquema.
        return data.findAllBarberId(barberId).map(mapper::toDomain);
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        return data.save(mapper.toEntity(reservation))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmployeeAndRange(
            UUID employeeId,
            Instant startTime,
            Instant endTime
    ) {
        return data.existsByEmployeeAndRange(
                employeeId, startTime, endTime
        );
    }

    @Override
    public Mono<Boolean> existsByCustomerAndRange(
            UUID customerId,
            Instant startTime,
            Instant endTime
    ) {
        return data.existsByCustomerAndRange(
                customerId, startTime, endTime
        );
    }

    @Override
    public Flux<Reservation> findWaitingQueueByEmployeeId(UUID employeeId) {
        return data.findWaitingQueueByEmployeeId(employeeId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reservation> findCurrentQueueTurn(UUID employeeId) {
        return data.findCurrentQueueTurn(employeeId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countPreviousWaitingTurns(
            UUID employeeId,
            Instant entryTime,
            UUID reservationId
    ) {
        return data.countPreviousWaitingTurns(
                employeeId, entryTime, reservationId
        );
    }
}