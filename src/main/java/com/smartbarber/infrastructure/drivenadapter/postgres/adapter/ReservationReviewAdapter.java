package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.domain.port.ReservationReviewPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ReservationReviewData;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationReviewEntity;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ReservationReviewAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ReservationReviewAdapter implements ReservationReviewPort {

    private final ReservationReviewData reservationReviewData;
    private final ReservationReviewAdapterMapper mapper;

    @Override
    public Mono<ReservationReview> findById(Integer id) {
        return reservationReviewData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> findByReservationId(UUID reservationId) {
        return reservationReviewData.findByReservationId(reservationId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> save(ReservationReview reservationReview) {
        return reservationReviewData.save(mapper.toEntity(reservationReview))
                .doOnNext(entity ->
                        log.info("Reservation review saved: {}", entity))
                .doOnSuccess(entity ->
                        log.info("Reservation review save operation completed"))
                .doOnError(error ->
                        log.error("Error saving reservation review", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<ReservationReview> update(
            Integer id,
            ReservationReview reservationReview
    ) {
        return Mono.defer(() -> {
            if (id == null
                    || reservationReview == null
                    || reservationReview.getId() == null
                    || !id.equals(reservationReview.getId())) {

                return Mono.error(new BusinessExceptions(
                        ReservationReviewMessageExceptions.INVALID_DATA
                ));
            }

            ReservationReviewEntity entity = mapper.toEntity(reservationReview);
            entity.setId(id);

            return reservationReviewData.save(entity)
                    .doOnNext(saved ->
                            log.info("Reservation review updated: {}", saved))
                    .doOnError(error ->
                            log.error("Error updating reservation review", error))
                    .map(mapper::toDomain);
        });
    }

    @Override
    public Mono<Boolean> existsByReservationId(UUID reservationId) {
        return reservationReviewData.existsByReservationId(reservationId);
    }
}