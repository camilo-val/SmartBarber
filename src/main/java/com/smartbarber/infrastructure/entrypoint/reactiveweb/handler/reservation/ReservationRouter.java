package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.RESERVATION;

@Configuration
public class ReservationRouter {
    @Bean
    public RouterFunction<ServerResponse> reservationRoutes(ReservationHandler reservationHandler) {
        return RouterFunctions.route(
                RequestPredicates.POST(RESERVATION + "/create"), reservationHandler::createReservation)
                .andRoute(RequestPredicates.GET(RESERVATION + "/{id}"), reservationHandler::getReservationById)
                .andRoute(RequestPredicates.GET(RESERVATION + "/employee/{employeeId}"), reservationHandler::getReservationByEmployeeId)
                .andRoute(RequestPredicates.GET(RESERVATION + "/customer/{customerId}"), reservationHandler::getReservationByCustomerId)
                .andRoute(RequestPredicates.GET(RESERVATION + "/barber/{barberId}"), reservationHandler::getReservationByBarberId);
    }
}
