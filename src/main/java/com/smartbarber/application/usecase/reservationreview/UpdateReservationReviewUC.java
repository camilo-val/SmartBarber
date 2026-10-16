package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.application.command.in.reservationreview.ReservationReviewCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.domain.port.ReservationReviewPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UpdateReservationReviewUC {

    private final ReservationReviewPort reservationReviewPort;

    public Mono<ReservationReview> updateReservationReview(
            Integer id,
            ReservationReviewCommand command
    ) {
        return reservationReviewPort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ReservationReviewMessageExceptions.RESERVATION_REVIEW_NOT_FOUND
                        )
                ))
                .flatMap(existingReview -> Mono.fromCallable(() ->
                        ReservationReview.update(
                                existingReview.getId(),
                                command.reservationId(),
                                command.qualification(),
                                command.comment(),
                                existingReview.getCreateAt()
                        )
                ).flatMap(updatedReview -> {
                    boolean reservationChanged = !Objects.equals(
                            existingReview.getReservationId(),
                            updatedReview.getReservationId()
                    );

                    if (!reservationChanged) {
                        return reservationReviewPort.update(id, updatedReview);
                    }

                    return reservationReviewPort.existsByReservationId(
                                    updatedReview.getReservationId()
                            )
                            .flatMap(exists -> {
                                if (Boolean.TRUE.equals(exists)) {
                                    return Mono.error(
                                            new BusinessExceptions(
                                                    ReservationReviewMessageExceptions.RESERVATION_REVIEW_ALREADY_EXISTS
                                            )
                                    );
                                }

                                return reservationReviewPort.update(id, updatedReview);
                            });
                }));
    }
}