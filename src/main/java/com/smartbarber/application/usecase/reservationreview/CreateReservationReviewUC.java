package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.domain.port.ReservationReviewPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateReservationReviewUC {

    private final ReservationReviewPort reservationReviewPort;

    public Mono<ReservationReview> createReservationReview(ReservationReview reservationReview) {
        return reservationReviewPort.existByCommentAndCreatedAt(
                reservationReview.getComment(),
                reservationReview.getCreateAt()
        )
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)){
                        return Mono.error(
                                new BusinessExceptions(
                                        ReservationReviewMessageExceptions.RESERVATION_REVIEW_ALREADY_EXISTS
                                )
                        );
                    }
                    return Mono.just(reservationReview);
                })
                .flatMap(reservationReviewPort::save);
    }
}
