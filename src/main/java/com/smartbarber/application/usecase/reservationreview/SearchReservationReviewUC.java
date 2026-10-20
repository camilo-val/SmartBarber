
package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.domain.port.ReservationReviewPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchReservationReviewUC {

    private final ReservationReviewPort reservationReviewPort;

    public Mono<ReservationReview> findById(Integer id) {
        return reservationReviewPort.findById(id)
                .doOnNext(review ->
                        log.info("Reservation review found: {}", review))
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ReservationReviewMessageExceptions.RESERVATION_REVIEW_NOT_FOUND
                        )
                ));
    }

    public Mono<ReservationReview> findByReservationId(UUID reservationId) {
        return reservationReviewPort.findByReservationId(reservationId)
                .doOnNext(review ->
                        log.info("Reservation review found: {}", review))
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ReservationReviewMessageExceptions.RESERVATION_REVIEW_NOT_FOUND
                        )
                ));
    }
}