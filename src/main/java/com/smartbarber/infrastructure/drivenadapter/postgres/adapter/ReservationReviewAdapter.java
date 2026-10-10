package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.ReservationReviewPort;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ReservationReviewData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ReservationReviewAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ReservationReviewAdapter implements ReservationReviewPort{
    private final ReservationReviewData reservationReviewData;
    private final ReservationReviewAdapterMapper mapper;

    @Override
    public Mono<ReservationReview> findById(Integer id){
        return reservationReviewData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> findByReservationId(UUID reservationId){
        return reservationReviewData.findByReservationId(reservationId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> save(ReservationReview reservationReview){
        return reservationReviewData.save(mapper.toEntity(reservationReview))
                .doOnNext( e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess( entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError( error ->
                        log.error("Error guardando review", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> update(Integer id, ReservationReview reservationReview){
        return reservationReviewData.save(mapper.toEntity(reservationReview))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existByCommentAndCreatedAt(
            String comment,
            Instant createAt
    ) {
        return reservationReviewData.existsByCommentAndCreateAt(
                comment,
                createAt
        );
    }
}
