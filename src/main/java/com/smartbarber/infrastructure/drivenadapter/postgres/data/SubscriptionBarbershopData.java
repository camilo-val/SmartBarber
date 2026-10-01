package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionBarbershopEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public interface SubscriptionBarbershopData extends ReactiveCrudRepository<SubscriptionBarbershopEntity, UUID> {
    Flux<SubscriptionBarbershopEntity> findByBarberIdAndStatus(UUID barberId, SubscriptionBarberStatus status);
    Flux<SubscriptionBarbershopEntity> findByBarberId(UUID barberId);
    @Query("""
            SELECT COUNT(*) >= 1
            from suscripcion_barberia
            where id_barberia = :barberId
            and estado in ('ACTIVE','PENDING');
            """)
    Mono<Boolean> countByBarberIdAndStatusIn(UUID barberId);

    @Query("""
            SELECT COUNT(*) = 1
            FROM suscripcion_barberia
            where id_barberia = :barberId
            and estado in ('ACTIVE')
            """)
    Mono<Boolean> countActiveSubscriptionsByBarberId(UUID barberId);

    @Query("""
            SELECT * FROM suscripcion_barberia
            WHERE renovacion_automatica = :automaticRenew
            AND fecha_expiracion >= :initialDate
            AND fecha_expiracion <= :finalDate
            """)
    Flux<SubscriptionBarbershopEntity> findByStatusAndAutomaticRenew(Instant initialDate, Instant finalDate,
                                                                     Boolean automaticRenew);

    @Query("""
            SELECT *
            FROM suscripcion_barberia
            WHERE fecha_expiracion <= :expirationDate
            AND estado = 'ACTIVE'
            """)
    Flux<SubscriptionBarbershopEntity> findByExpirationDate(Instant expirationDate);



    @Query("""
            SELECT COUNT(*) >= 1
            from suscripcion_barberia
            where id_barberia = :barberId
            and estado in ('APPROVED','PENDING');
            """)
    Mono<Boolean> existsSubscriptionsPendingOrApproved(UUID barberId);


}
