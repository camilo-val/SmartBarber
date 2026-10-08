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
    public Mono<Reservation> findById(UUID reservationId) {
        return data.findById(reservationId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID reservationId) {
        return data.existsById(reservationId);
    }

    @Override
    public Flux<Reservation> findByEmployeeId(UUID employeeId) {
        return data.findByEmployeeId(employeeId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Reservation> findByCustomerId(UUID customerId) {
        return data.findByCustomerId(customerId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        System.out.println("Saving reservation: " + reservation);
        System.out.println("Mapped entity: " + mapper.toEntity(reservation));
        return data.save(mapper.toEntity(reservation))
                .doOnNext(res -> System.out.println("Reservation saved: " + res))
                .doOnError(error -> System.err.println("Error saving reservation: " + error.getMessage()))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmployeeAndRange(UUID employeeId, Instant startTime, Instant endTime) {
        System.out.println("Checking if reservation exists for employee: " + employeeId + " between " + startTime + " and " + endTime);
        return data.existsByEmployeeAndRange(employeeId, startTime, endTime);
    }

    @Override
    public Mono<Boolean> existsByCustomerAndRange(UUID customerId, Instant startTime, Instant endTime) {
        return data.existsByCustomerAndRange(customerId, startTime, endTime);
    }
}
