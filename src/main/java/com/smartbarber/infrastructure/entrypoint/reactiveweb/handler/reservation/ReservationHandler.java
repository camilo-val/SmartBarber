package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservation;

import com.smartbarber.application.command.in.reservation.ReservationCommand;
import com.smartbarber.application.usecase.reservation.servicereservation.CreateReservationUc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReservationHandler {
    private final CreateReservationUc createReservationUc;

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        return request.bodyToMono(ReservationCommand.class)
                .flatMap(createReservationUc::createReservation)
                .flatMap(reservation -> ServerResponse.ok().bodyValue(reservation))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
