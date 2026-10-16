package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.application.command.in.reservationreview.ReservationReviewCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.domain.port.ReservationReviewPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateReservationReviewUC {

    private final ReservationReviewPort reservationReviewPort;

    public Mono<ReservationReview> createReservationReview(
            ReservationReviewCommand command
    ) {
        return Mono.fromCallable(() ->
                ReservationReview.createReservationReview(
                        null,
                        command.reservationId(),
                        command.qualification(),
                        command.comment()
                )
        ).flatMap(reservationReview ->
                reservationReviewPort.existsByReservationId(
                        reservationReview.getReservationId()
                ).flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(
                                new BusinessExceptions(
                                        ReservationReviewMessageExceptions.RESERVATION_REVIEW_ALREADY_EXISTS
                                )
                        );
                    }

                    return reservationReviewPort.save(reservationReview);
                })
        );
    }
}