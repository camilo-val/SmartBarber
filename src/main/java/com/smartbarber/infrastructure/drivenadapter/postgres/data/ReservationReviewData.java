package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationReviewEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;


public interface ReservationReviewData
        extends ReactiveCrudRepository<ReservationReviewEntity, Integer> {

    Mono<ReservationReviewEntity> findById(Integer id);

    Mono<ReservationReviewEntity> findByReservationId(UUID reservationId);

    Mono<Boolean> existsByCommentAndCreateAt(
            String comment,
            Instant createAt
    );
}
