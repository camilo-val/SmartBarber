package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservation;

import com.smartbarber.application.command.in.reservation.ReservationCommand;
import com.smartbarber.application.usecase.reservation.servicereservation.CreateReservationUc;
import com.smartbarber.application.usecase.reservation.servicereservation.FindReservationUc;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationHandler {
    private final CreateReservationUc createReservationUc;
    private final FindReservationUc findReservationUc;

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        return request.bodyToMono(ReservationCommand.class)
                .flatMap(createReservationUc::createReservation)
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation))
                .switchIfEmpty(Mono.error(() -> new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> getReservationById(ServerRequest request) {
        String id = request.pathVariable("id");
        return findReservationUc.findReservationById(UUID.fromString(id))
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation));
    }

    public Mono<ServerResponse> getReservationByEmployeeId(ServerRequest request) {
        String id = request.pathVariable("employeeId");
        return findReservationUc.findByEmployeeId(UUID.fromString(id))
                .collectList()
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation));
    }

    public Mono<ServerResponse> getReservationByCustomerId(ServerRequest request) {
        String id = request.pathVariable("customerId");
        return findReservationUc.findByCustomerId(UUID.fromString(id))
                .collectList()
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation));
    }

    public Mono<ServerResponse> getReservationByBarberId(ServerRequest request) {
        String id = request.pathVariable("barberId");
        return findReservationUc.findAllBarberId(UUID.fromString(id))
                .collectList()
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation));
    }
}
