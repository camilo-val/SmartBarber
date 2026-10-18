/*package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.queue;

import com.smartbarber.application.usecase.queue.CloseQueueUC;
import com.smartbarber.application.usecase.queue.NextCustomerUC;
import com.smartbarber.application.usecase.queue.OpenQueueUC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VirtualQueueHandler {

    private final OpenQueueUC openQueueUC;
    private final NextCustomerUC nextCustomerUC;
    private final CloseQueueUC closeQueueUC;

    public Mono<ServerResponse> openQueue(
            ServerRequest request
    ) {

        String barbershopId =
                request.pathVariable("barbershopId");

        return openQueueUC.execute(
                        barbershopId
                )
                .then(
                        ServerResponse.ok()
                                .build()
                );
    }

    public Mono<ServerResponse> nextCustomer(
            ServerRequest request
    ) {

        String barbershopId =
                request.pathVariable("barbershopId");

        return nextCustomerUC.execute(
                        barbershopId
                )
                .then(
                        ServerResponse.ok()
                                .build()
                );
    }

    public Mono<ServerResponse> closeQueue(
            ServerRequest request
    ) {

        String barbershopId =
                request.pathVariable("barbershopId");

        return closeQueueUC.execute(
                        barbershopId
                )
                .then(
                        ServerResponse.ok()
                                .build()
                );
    }
}*/