package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.domain.port.ReservationReviewPort;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UpdateReservationReviewUC {

    private final ReservationReviewPort reservationReviewPort;

    public Mono<ReservationReview> reservationReviewUpdate(String id, ReservationReview reservationReview){
        return reservationReviewPort.findById(Integer.valueOf(id))
                .map(reservationReviewDB -> ReservationReview.update(reservationReviewDB.getId(),
                        reservationReview.getReservationId(),reservationReview.getQualification(),
                        reservationReview.getComment(),reservationReviewDB.getCreateAt()))
                .flatMap(reservationReviewUpdate -> reservationReviewPort.update(Integer.valueOf(id), reservationReviewUpdate));
    }
}
