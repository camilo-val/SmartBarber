
package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberQueueData
        extends ReactiveCrudRepository<BarberQueueEntity, UUID> {

    Mono<BarberQueueEntity> findByEmployeeId(UUID employeeId);

    @Modifying
    @Query("""
        INSERT INTO public.cola_barbero (
            id_cola_barbero,
            id_empleado,
            id_barberia,
            aceptando_clientes,
            version,
            fecha_creacion,
            fecha_modificacion
        )
        VALUES (
            gen_random_uuid(),
            :employeeId,
            :barbershopId,
            FALSE,
            0,
            NOW(),
            NOW()
        )
        ON CONFLICT (id_empleado) DO NOTHING
        """)
    Mono<Integer> insertIfAbsent(
            @Param("employeeId") UUID employeeId,
            @Param("barbershopId") UUID barbershopId
    );

    @Query("""
        SELECT *
        FROM public.cola_barbero
        WHERE id_empleado = :employeeId
        FOR UPDATE
        """)
    Mono<BarberQueueEntity> findByEmployeeIdForUpdate(
            @Param("employeeId") UUID employeeId
    );

    @Modifying
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

    Mono<Boolean> existsByEmployeeIdAndBarbershopIdAndAcceptingClients(
            @Param("employeeId") UUID employeeId,
            @Param("barbershopId") UUID barbershopId,
            @Param("acceptingClients") boolean acceptingClients
    );
}
