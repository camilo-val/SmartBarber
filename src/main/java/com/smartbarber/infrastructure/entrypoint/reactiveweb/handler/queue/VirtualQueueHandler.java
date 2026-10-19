
package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.queue;

import com.smartbarber.application.command.in.reservation.ReservationCommand;
import com.smartbarber.application.usecase.queue.CloseQueueUC;
import com.smartbarber.application.usecase.queue.CompleteCurrentTurnUC;
import com.smartbarber.application.usecase.queue.NextCustomerUC;
import com.smartbarber.application.usecase.queue.OpenQueueUC;
import com.smartbarber.application.usecase.queue.QueueBroadcastService;
import com.smartbarber.application.usecase.reservation.servicereservation.CreateReservationQueueUc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VirtualQueueHandler {

    private final OpenQueueUC openQueueUC;
    private final NextCustomerUC nextCustomerUC;
    private final CloseQueueUC closeQueueUC;
    private final CreateReservationQueueUc createReservationQueueUc;
    private final QueueBroadcastService queueBroadcastService;
    private final CompleteCurrentTurnUC completeCurrentTurnUC;

    public Mono<ServerResponse> openQueue(ServerRequest request) {
        UUID employeeId = UUID.fromString(
                request.pathVariable("employeeId")
        );

        return openQueueUC.execute(employeeId)
                .then(ServerResponse.ok().bodyValue(
                        Map.of(
                                "message", "Cola abierta correctamente",
                                "employeeId", employeeId
                        )
                ));
    }

    public Mono<ServerResponse> closeQueue(ServerRequest request) {
        UUID employeeId = UUID.fromString(
                request.pathVariable("employeeId")
        );

        return closeQueueUC.execute(employeeId)
                .then(ServerResponse.ok().bodyValue(
                        Map.of(
                                "message", "Cola cerrada para nuevos clientes",
                                "employeeId", employeeId
                        )
                ));
    }

    public Mono<ServerResponse> nextCustomer(ServerRequest request) {
        UUID employeeId = UUID.fromString(
                request.pathVariable("employeeId")
        );

        return nextCustomerUC.execute(employeeId)
                .flatMap(reservation ->
                        ServerResponse.ok().bodyValue(reservation)
                );
    }

    public Mono<ServerResponse> joinQueue(ServerRequest request) {
        return request.bodyToMono(ReservationCommand.class)
                .flatMap(createReservationQueueUc::createReservation)
                .flatMap(reservation ->
                        ServerResponse.created(
                                URI.create(
                                        "/reservation-service/"
                                                + reservation.getId()
                                )
                        ).bodyValue(reservation)
                );
    }

    public Mono<ServerResponse> completeCurrentTurn(
            ServerRequest request
    ) {
        UUID employeeId = UUID.fromString(
                request.pathVariable("employeeId")
        );

        return completeCurrentTurnUC.execute(employeeId)
                .flatMap(reservation ->
                        ServerResponse.ok().bodyValue(reservation)
                );
    }

    public Mono<ServerResponse> getQueue(ServerRequest request) {
        UUID employeeId = UUID.fromString(
                request.pathVariable("employeeId")
        );

        return queueBroadcastService.getSnapshot(employeeId)
                .flatMap(snapshot ->
                        ServerResponse.ok().bodyValue(snapshot)
                );
    }
}
