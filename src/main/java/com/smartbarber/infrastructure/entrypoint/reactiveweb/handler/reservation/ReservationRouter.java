package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReservationRouter {
    @Bean
    public RouterFunction<ServerResponse> reservationRoutes(ReservationHandler reservationHandler) {
        return RouterFunctions.route(
                RequestPredicates.POST("/reservations"),
                reservationHandler::createReservation
        );
    }
}
