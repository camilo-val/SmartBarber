package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberQueueData
        extends ReactiveCrudRepository<BarberQueueEntity, UUID> {

    Mono<BarberQueueEntity> findByEmployeeId(UUID employeeId);

    @Query("""
        SELECT *
        FROM public.cola_barbero
        WHERE id_empleado = :employeeId
        FOR UPDATE
        """)
    Mono<BarberQueueEntity> findByEmployeeIdForUpdate(
            @Param("employeeId") UUID employeeId
    );

    @Query("""
        UPDATE public.cola_barbero
        SET aceptando_clientes = :acceptingClients,
            version = version + 1,
            fecha_modificacion = NOW()
        WHERE id_empleado = :employeeId
        """)
    Mono<Integer> updateAcceptingClients(
            @Param("employeeId") UUID employeeId,
            @Param("acceptingClients") boolean acceptingClients
    );
}