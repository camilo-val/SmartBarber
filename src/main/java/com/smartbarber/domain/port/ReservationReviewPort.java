package com.smartbarber.domain.port;

import com.smartbarber.domain.model.reservationreview.ReservationReview;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public interface ReservationReviewPort {
    Mono<ReservationReview> findById(Integer id);
    Mono<ReservationReview> findByReservationId(UUID reservationId);
    Mono<ReservationReview> save(ReservationReview reservationReview);
    Mono<ReservationReview> update(Integer id, ReservationReview reservationReview);
    Mono<Boolean> existByCommentAndCreatedAt(String comment, Instant createAt);
}
