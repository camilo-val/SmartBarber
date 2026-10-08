package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public interface ReservationData extends ReactiveCrudRepository<ReservationEntity, UUID> {
    Flux<ReservationEntity> findByEmployeeId(UUID employeeId);
    Flux<ReservationEntity> findByCustomerId(UUID customerId);
    @Query("""
            SELECT EXISTS (
                SELECT 1
                FROM reserva
                WHERE id_empleado = :employeeId
                AND fecha_inicio_real < :endTime
                AND fecha_fin_real > :startTime
            )
            """)
    Mono<Boolean> existsByEmployeeAndRange(UUID employeeId, Instant startTime, Instant endTime);
    @Query("""
            SELECT EXISTS (
                SELECT 1
                FROM reserva
                WHERE id_cliente = :customerId
                AND fecha_inicio_real < :endTime
                AND fecha_fin_real > :startTime
            )
            """)
    Mono<Boolean> existsByCustomerAndRange(UUID customerId, Instant startTime, Instant endTime);

    @Query("""
            SELECT *
            FROM reserva r
            JOIN empleado e ON r.id_empleado = e.id
            WHERE e.id_barbero = :barberId
            """)
    Flux<ReservationEntity> findAllBarberId(UUID barberId);
}
