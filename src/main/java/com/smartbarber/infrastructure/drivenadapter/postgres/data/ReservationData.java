
package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public interface ReservationData
        extends ReactiveCrudRepository<ReservationEntity, UUID> {

    Flux<ReservationEntity> findByEmployeeId(UUID employeeId);

    Flux<ReservationEntity> findByCustomerId(UUID customerId);

    @Query("""
        SELECT *
        FROM public.reserva
        WHERE id_empleado = :employeeId
          AND tipo_reserva = 'VIRTUAL_QUEUE'
          AND estado = 'WAITING'
        ORDER BY fecha_ingreso_cola ASC, id_reserva ASC
        """)
    Flux<ReservationEntity> findWaitingQueueByEmployeeId(
            @Param("employeeId") UUID employeeId
    );

    @Query("""
        SELECT *
        FROM public.reserva
        WHERE id_empleado = :employeeId
          AND tipo_reserva = 'VIRTUAL_QUEUE'
          AND estado = 'IN_PROGRESS'
        LIMIT 1
        """)
    Mono<ReservationEntity> findCurrentQueueTurn(
            @Param("employeeId") UUID employeeId
    );

    @Query("""
        SELECT COUNT(*)
        FROM public.reserva
        WHERE id_empleado = :employeeId
          AND tipo_reserva = 'VIRTUAL_QUEUE'
          AND estado = 'WAITING'
          AND (
                fecha_ingreso_cola < :entryTime
                OR (
                    fecha_ingreso_cola = :entryTime
                    AND id_reserva < :reservationId
                )
          )
        """)
    Mono<Long> countPreviousWaitingTurns(
            @Param("employeeId") UUID employeeId,
            @Param("entryTime") Instant entryTime,
            @Param("reservationId") UUID reservationId
    );

    @Query("""
        SELECT EXISTS (
            SELECT 1
            FROM public.reserva
            WHERE id_empleado = :employeeId
              AND fecha_inicio_real < :endTime
              AND fecha_fin_real > :startTime
        )
        """)
    Mono<Boolean> existsByEmployeeAndRange(
            @Param("employeeId") UUID employeeId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );

    @Query("""
        SELECT EXISTS (
            SELECT 1
            FROM public.reserva
            WHERE id_cliente = :customerId
              AND fecha_inicio_real < :endTime
              AND fecha_fin_real > :startTime
        )
        """)
    Mono<Boolean> existsByCustomerAndRange(
            @Param("customerId") UUID customerId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );

    @Query("""
        SELECT r.*
        FROM public.reserva r
        INNER JOIN public.empleado e
            ON e.id_empleado = r.id_empleado
        WHERE e.user_id = :barberId
        ORDER BY r.fecha_creacion DESC
        """)
    Flux<ReservationEntity> findAllBarberId(
            @Param("barberId") UUID barberId
    );
}
