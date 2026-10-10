package com.smartbarber.application.usecase.reservationreview;

import com.smartbarber.domain.port.ReservationReviewPort;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
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

    public Mono<ReservationReview> buscarPorId(Integer id){
        return reservationReviewPort.findById(id)
                .doOnNext(reservationReview -> log.info("Datos encontrados {}", reservationReview))
                .switchIfEmpty(Mono.error(new BusinessExceptions(ReservationReviewMessageExceptions.RESERVATION_REVIEW_NOT_FOUND)));
    }

    public Mono<ReservationReview> buscarPorIdReserva(UUID reservationId) {
        return reservationReviewPort.findByReservationId(reservationId)
                .doOnNext(review -> log.info("Datos encontrados {}", review))
                .switchIfEmpty(
                        Mono.error(
                                new BusinessExceptions(
                                        ReservationReviewMessageExceptions
                                                .RESERVATION_REVIEW_NOT_FOUND
                                )
                        )
                );
    }
}
