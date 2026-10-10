package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservationreview;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.RESERVATION_REVIEW_SERVICE;

@Configuration
@AllArgsConstructor
public class ReservationReviewRuta {

    private final ReservationReviewHandler reservationReviewHandler;

    @Bean
    public RouterFunction<ServerResponse> reservationReviewRutas() {


        return RouterFunctions.route(RequestPredicates.POST( RESERVATION_REVIEW_SERVICE + "/crear-resena-reserva"),reservationReviewHandler::createReservationReview)
                .andRoute(RequestPredicates.GET(RESERVATION_REVIEW_SERVICE + "/id/{id}"),reservationReviewHandler::findReservationReviewById)
                .andRoute(RequestPredicates.GET(RESERVATION_REVIEW_SERVICE + "/reserva/{reservationId}"),reservationReviewHandler::findReservationReviewByReservationId)
                .andRoute(RequestPredicates.PUT(RESERVATION_REVIEW_SERVICE + "/actualizar-resena-reserva/{id}"),reservationReviewHandler::updateReservationReview);
    }
}
